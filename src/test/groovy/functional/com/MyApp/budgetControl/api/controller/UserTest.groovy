package com.MyApp.budgetControl.api.controller

import com.MyApp.budgetControl.api.CommonTest

import static io.restassured.RestAssured.delete
import static io.restassured.RestAssured.get
import static io.restassured.RestAssured.given

class UserTest extends CommonTest {

    def "get users should return list of users and correct parameters code 200 and App Json Content Type"() {
        given:
            createUser("user1")
            createUser("user2")

        when:
            def response = get("/users")

        then:
            response.statusCode() == 200
            response.contentType() == "application/json"
            response.jsonPath()
                    .getList("userName")
                    .containsAll("user1", "user2")
    }

    def "get user by Id when exist should return information about user and correct parameters code 200 and App Json Content Type"() {
        given:
            def userName = "userById"
            createUser(userName)
            def userId = getUserIdByUserName(userName)

        when:
            def response = get("/users/%s".formatted(userId))

        then:
            response.statusCode() == 200
            response.jsonPath()
                    .getString("userName")
                    .contains(userName)
            response.jsonPath()
                    .getString("userId")
                    .contains(userId)
            response.jsonPath()
                    .getList("userExpenses").isEmpty()
    }

    def "get user by Id when not exist should throw not found error"() {
        when:
            def response = get("/users/notExistUserId")

        then:
            assertErrorMessage(response, "NOT_FOUND")
    }

    def "save new user should add it to repository and return status created 201"() {
        given:
            def userName = "testUserCreated1"

        when:
            given()
                    .contentType("application/json")
                    .body([userName : userName,
                           userEmail: "%s@mail.com".formatted(userName)])
                    .post("/users")
                    .then()
                    .statusCode(201)
        then:
            get("/users").jsonPath()
                    .getList("userName")
                    .contains(userName)

    }

    def "save new user with already used userName should throw an conflict error"() {
        given:
            def userName = "userThatAlreadyExist"
            createUser(userName)

        when:
            def postResponse = given()
                    .contentType("application/json")
                    .body([userName : userName,
                           userEmail: "%s@mail.com".formatted(userName)])
                    .post("/users")
                    .then()
                    .statusCode(409)
        then:
            assertErrorMessage(postResponse.extract().response(), "CONFLICT_ERROR")

    }

    def "delete user when exist should remove it from repository and return status 202"() {
        given:
            def userName = "userToDelete"
            createUser(userName)
            def userId = getUserIdByUserName(userName)

        when:
            delete("/users/%s".formatted(userId))
                    .then()
                    .statusCode(202)

        then:
            def response = get("/users/%s".formatted(userId))
            assertErrorMessage(response, "NOT_FOUND")
    }

    def "delete user when not exist should throw not found error"() {
        when:
            def deleteResponse = delete("/users/notExistingUserId")
                    .then()
                    .statusCode(404)

        then:
            assertErrorMessage(deleteResponse.extract().response(), "NOT_FOUND")
    }

}
