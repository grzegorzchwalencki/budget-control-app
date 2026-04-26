package com.MyApp.budgetControl.api.controller

import com.MyApp.budgetControl.api.CommonTest
import com.MyApp.budgetControl.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired

import static io.restassured.RestAssured.delete
import static io.restassured.RestAssured.get
import static io.restassured.RestAssured.given

class UserApiSpec extends CommonTest {

    @Autowired
    UserRepository repository

    def "get users should return list of users and correct parameters code 200 and App Json Content Type"() {
        given:
            def userName1 = "user1"
            def userName2 = "user2"
            createUser(userName1)
            createUser(userName2)

        when:
            def response = get(USERS_PATH)

        then:
            with(response) {
                assert statusCode() == 200
                assert contentType() == CONTENT_TYPE_JSON
                assert jsonPath().getList("userName").containsAll(userName1, userName2)
            }
    }

    def "get user by Id when exist should return information about user and correct code 200"() {
        given:
            def userName = "userById"
            createUser(userName)
            def id = getUserIdByUserName(userName)

        when:
            def response = get(USERS_PATH + "/%s".formatted(id))

        then:
            response.statusCode() == 200

        and:
            with(response.jsonPath()) {
                assert getString("userName").contains(userName)
                assert getString("userId").contains(id)
                assert getList("userExpenses").isEmpty()
            }
    }

    def "get user by Id when not exist should throw not found error"() {
        when:
            def response = get(USERS_PATH + "/notExistUserId")

        then:
            assertErrorMessage(response, ExpectedErrors.NOT_FOUND)
    }

    def "save new user should add it to repository return status created 201 and correct correct response body"() {
        given:
            def userName = "testUserCreated1"
            def payload = [userName : userName,
                           userEmail: "%s@mail.com".formatted(userName)]

        when:
            def response = given()
                    .contentType(CONTENT_TYPE_JSON)
                    .body(payload)
                    .post(USERS_PATH)

            def body = response.jsonPath()
            def id = body.getString("userId")

        then:
            response.statusCode() == 201

        and:
            with(body) {
                getString("userId") != null
                getString("userName") == userName
            }

        and:
            repository.findById(id).isPresent()

    }

    def "save new user with already used userName should throw an conflict error"() {
        given:
            def userName = "userThatAlreadyExist"
            createUser(userName)
            def payload = [userName : userName,
                           userEmail: "%s@mail.com".formatted(userName)]

        when:
            def postResponse = given()
                    .contentType(CONTENT_TYPE_JSON)
                    .body(payload)
                    .post(USERS_PATH)

        then:
            postResponse.statusCode() == 409

        and:
            assertErrorMessage(postResponse.then().extract().response(), ExpectedErrors.CONFLICT_ERROR)

    }

    def "delete user when exist should remove it from repository and return status 202"() {
        given:
            def userName = "userToDelete"
            createUser(userName)
            def userId = getUserIdByUserName(userName)

        when:
            def deleteResponse = delete(USERS_PATH + "/%s".formatted(userId))

        then:
            deleteResponse.statusCode() == 202

        and:
            !repository.findById(userId).isPresent()
    }

    def "delete user when not exist should throw not found error"() {
        when:
            def deleteResponse = delete(USERS_PATH + "/notExistingUserId")

        then:
            deleteResponse.statusCode() == 404

        and:
            assertErrorMessage(deleteResponse.then().extract().response(), ExpectedErrors.NOT_FOUND)
    }

}
