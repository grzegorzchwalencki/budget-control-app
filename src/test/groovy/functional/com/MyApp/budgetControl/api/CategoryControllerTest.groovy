package com.MyApp.budgetControl.api

import static io.restassured.RestAssured.get
import static io.restassured.RestAssured.given

class CategoryControllerTest extends CommonTest {

    def "get categories should return list of categories and correct parameters code 200 and app json content type"() {
        given:
            createCategory("category 1")
            createCategory("category 2")

        when:
            def response = get("/categories")

        then:
            response.statusCode() == 200
            response.contentType() == "application/json"
            response.jsonPath()
                    .getList("categoryName")
                    .containsAll("category 1", "category 2")
    }

    def "save new category should add it to repository and return status created 201"() {
        when:
            given()
                    .contentType("application/json")
                    .body([categoryName: "expected category"])
                    .when()
                    .post("/categories")
                    .then()
                    .statusCode(201)
        then:
            get("/categories").jsonPath()
                    .getList("categoryName")
                    .contains("expected category")

    }

    def "save new category with empty categoryName field should return BadRequest with expected errorDetails"() {
        when:
            def result = given()
                    .contentType("application/json")
                    .body([categoryName: categoryName])
                    .when()
                    .post("/categories")
                    .then()
                    .statusCode(expectedStatusCode)
                    .extract()
                    .body().jsonPath()
        then:
            result.get("errorDetails").toString() == "[Category name is mandatory]"

        where:
            categoryName | expectedStatusCode
            null         | 400
            ""           | 400
            "   "        | 400
    }

    def "save new category with categoryName grater than maximum field should return BadRequest with expected errorDetails"() {
        given:
            def MAX_CATEGORY_NAME_LENGTH = 64
        when:
            def result = given()
                    .contentType("application/json")
                    .body([categoryName: "a".repeat(MAX_CATEGORY_NAME_LENGTH + 1)])
                    .when()
                    .post("/categories")
                    .then()
                    .statusCode(400)
                    .extract()
                    .body().jsonPath()
        then:
            result.get("errorDetails").toString() == "[Category name max length is 64 char]"

    }

}
