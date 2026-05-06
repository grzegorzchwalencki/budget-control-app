package com.MyApp.budgetControl.api.controller

import com.MyApp.budgetControl.api.CommonTest
import com.MyApp.budgetControl.domain.expense.ExpenseRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import static io.restassured.RestAssured.delete
import static io.restassured.RestAssured.get
import static io.restassured.RestAssured.given

class ExpenseApiSpec extends CommonTest {

    @Shared
    String categoryId
    @Shared
    String userId

    def categoryName = faker.name().femaleFirstName()
    def userName = faker.name().firstName()

    @Autowired
    ExpenseRepository repository

    def setup() {
        categoryId = createCategory(categoryName)
        userId = createUser(userName)
    }

    def "get expenses method should return list with expected records, code 200 and appJson contentType"() {
        given:
            def expenseId1 = createExpense(categoryId, userId)
            def expenseId2 = createExpense(categoryId, userId)

        when:
            def response = get(EXPENSES_PATH)

        then:
            response.statusCode() == 200
            response.contentType() == CONTENT_TYPE_JSON

        and:
            response.jsonPath()
                    .getList("expenseId").containsAll(expenseId1, expenseId2)

    }

    def "get expense by Id when exist should return information about expense and code 200"() {
        given:
            def expenseId = createExpense(categoryId, userId)

        when:
            def response = get(EXPENSES_PATH + "/" + expenseId)

        then:
            response.statusCode() == 200

        and:
            with(response.jsonPath()) {
                assert getString("expenseId") == expenseId
                assert getString("userId") == userId
                assert getString("categoryId") == categoryId
            }
    }

    def "get expense by Id when not exist should throw not found error"() {
        when:
            def response = get(EXPENSES_PATH + "/notExistUserId")

        then:
            assertErrorMessage(response, ExpectedErrors.NOT_FOUND)
    }

    def "save new expense should add it to repository return status created 201 and correct correct response body"() {
        given:
            def expenseComment = "testComment"
            def payload = [expenseCost   : 777,
                           categoryId    : categoryId,
                           userId        : userId,
                           expenseComment: expenseComment]

        when:
            def response = given()
                    .contentType(CONTENT_TYPE_JSON)
                    .body(payload)
                    .post(EXPENSES_PATH)

            def body = response.jsonPath()
            def id = body.getString("expenseId")

        then:
            response.statusCode() == 201

        and:
            with(body) {
                getString("expenseId") != null
                getInt("expenseCost") == 777
                getString("userId") == userId
                getString("categoryId") == categoryId
                getString("expenseComment") == expenseComment
            }

        and:
            repository.findById(id).isPresent()

    }

    def "save new expense with request that fail validations should throw an validation error"() {
        given:
            def payload = [expenseCost   : cost,
                           categoryId    : categoryId,
                           userId        : userId,
                           expenseComment: comment]

        when:
            def postResponse = given()
                    .contentType(CONTENT_TYPE_JSON)
                    .body(payload)
                    .post(EXPENSES_PATH)

        then:
            println postResponse.asString()
            postResponse.statusCode() == 400

        and:
            assertErrorMessage(postResponse.then().extract().response(), expectedError, expectedErrorDetails)


        where:
            cost | comment                                    | expectedError                   | expectedErrorDetails
            111  | ""                                         | ExpectedErrors.VALIDATION_ERROR | "Comment is mandatory"
            null | "Comment"                                  | ExpectedErrors.VALIDATION_ERROR | "Cost value is mandatory"
            0    | "Comment"                                  | ExpectedErrors.VALIDATION_ERROR | "Cost value should be positive"
            123  | "a".repeat(MAX_EXPENSE_COMMENT_LENGTH + 1) | ExpectedErrors.VALIDATION_ERROR | "Comment max length is 128 char"

    }

    def "save new expense with request does not meet the validation requirements should throw an validation error"() {
        given:
            def payload = [expenseCost   : cost,
                           categoryId    : categoryId,
                           userId        : userId,
                           expenseComment: comment]

        when:
            def postResponse = given()
                    .contentType(CONTENT_TYPE_JSON)
                    .body(payload)
                    .post(EXPENSES_PATH)

        then:
            println postResponse.asString()
            postResponse.statusCode() == 400

        and:
            assertErrorMessage(postResponse.then().extract().response(), expectedError, expectedErrorDetails)


        where:
            cost | comment                                    | expectedError                   | expectedErrorDetails
            111  | ""                                         | ExpectedErrors.VALIDATION_ERROR | "Comment is mandatory"
            null | "Comment"                                  | ExpectedErrors.VALIDATION_ERROR | "Cost value is mandatory"
            0    | "Comment"                                  | ExpectedErrors.VALIDATION_ERROR | "Cost value should be positive"
            123  | "a".repeat(MAX_EXPENSE_COMMENT_LENGTH + 1) | ExpectedErrors.VALIDATION_ERROR | "Comment max length is 128 char"

    }

    def "delete expense when exist should remove it from repository and return status 202"() {
        given:
            def expenseToDeleteId = createExpense(categoryId, userId)

        when:
            def deleteResponse = delete(EXPENSES_PATH + "/%s".formatted(expenseToDeleteId))

        then:
            deleteResponse.statusCode() == 202

        and:
            !repository.findById(expenseToDeleteId).isPresent()
    }

    def "delete expense when not exist should throw not found error"() {
        when:
            def deleteResponse = delete(EXPENSES_PATH + "/notExistingUserId")

        then:
            deleteResponse.statusCode() == 404

        and:
            assertErrorMessage(deleteResponse.then().extract().response(), ExpectedErrors.NOT_FOUND)
    }

}
