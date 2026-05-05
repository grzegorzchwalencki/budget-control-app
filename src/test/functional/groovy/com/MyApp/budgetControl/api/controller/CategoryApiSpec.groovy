package com.MyApp.budgetControl.api.controller

import com.MyApp.budgetControl.api.CommonTest
import com.MyApp.budgetControl.domain.category.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired

import static io.restassured.RestAssured.get
import static io.restassured.RestAssured.given

class CategoryApiSpec extends CommonTest {

    @Autowired
    CategoryRepository repository

    def "get categories should return list of categories and correct parameters code 200 and app json content type"() {
        given:
            createCategory("category 1")
            createCategory("category 2")

        when:
            def response = get(CATEGORIES_PATH)

        then:
            response.statusCode() == 200
            response.contentType() == CONTENT_TYPE_JSON
            response.jsonPath()
                    .getList("categoryName")
                    .containsAll("category 1", "category 2")
    }

    def "save new category should add it to repository and return status created 201"() {
        given:
            def name = "expected category"
            def payload = [categoryName: name]

        when:
            def response = given()
                    .contentType(CONTENT_TYPE_JSON)
                    .body(payload)
                    .post(CATEGORIES_PATH)

            def body = response.jsonPath()
            def id = body.getString("categoryId")

        then:
            response.statusCode() == 201

        and:
            with(body) {
                getString("categoryId") != null
                getString("categoryName") == name
            }

        and:
            repository.findById(id).isPresent()

    }

    def "save new category with invalid categoryName should return Bad Request"() {
        given:
            def payload = [categoryName: categoryName]

        when:
            def response = given()
                    .contentType(CONTENT_TYPE_JSON)
                    .body(payload)
                    .post(CATEGORIES_PATH)

        then:
            response.statusCode == 400

        and:
            assertErrorMessage(response.then().extract().response(), expectErrorType, expectedErrorDetails)

        where:
            categoryName                             | expectErrorType                 | expectedErrorDetails
            null                                     | ExpectedErrors.VALIDATION_ERROR | "[Category name is mandatory]"
            ""                                       | ExpectedErrors.VALIDATION_ERROR | "[Category name is mandatory]"
            "   "                                    | ExpectedErrors.VALIDATION_ERROR | "[Category name is mandatory]"
            "a".repeat(MAX_CATEGORY_NAME_LENGTH + 1) | ExpectedErrors.VALIDATION_ERROR | "[Category name max length is 64 char]"
    }
}
