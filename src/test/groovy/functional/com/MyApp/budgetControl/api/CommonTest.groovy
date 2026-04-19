package com.MyApp.budgetControl.api

import io.restassured.response.Response

import static io.restassured.RestAssured.given

class CommonTest extends FunctionalTestConfiguration {

    def assertErrorMessage(Response response, String expectedError) {

        def expected = switch (expectedError) {
            case "NOT_FOUND" -> [statusCode: 404, errorDetails: "[Element with given Id does not exist]", errorType: "NOT_FOUND_ERROR"]
            case "CONFLICT_ERROR" -> [statusCode: 409, errorDetails: "[Name is already used. Please choose a different one]", errorType: "CONFLICT_ERROR"]
            default -> throw new IllegalArgumentException("Unknown error type: $expectedError")
        }

        assert response.statusCode() == expected.statusCode
        assert response.jsonPath().getInt("statusCode") == expected.statusCode
        assert response.jsonPath().getString("errorDetails").contains(expected.errorDetails)
        assert response.jsonPath().getString("errorType") == expected.errorType
        return true
    }

    def createCategory(String categoryName) {
        def payload = [categoryName: categoryName]
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/categories")
                .then()
                .statusCode(201)
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

    def getUserIdByUserName(String userName) {
        return given()
                .when().get("/users")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("find {it.userName == '${userName}'}.userId")
    }

}
