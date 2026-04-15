package com.MyApp.budgetControl.api


import static io.restassured.RestAssured.get
import static io.restassured.RestAssured.given

class UserControllerTest extends FunctionalTestConfiguration {


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

    def "get user by Id should return list of users and correct parameters code 200 and App Json Content Type"() {
        given:
            createUser("userById")
            def userId = get("/users")
                    .jsonPath().get().grep().stream().to
            println userId
        when:
            println userId
        then:
            println userId

    }



    def createUser(String userName) {
        def payload = [userName: userName, userEmail: "%s@mail.com".formatted(userName)]

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
    }
}
