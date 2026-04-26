package com.MyApp.budgetControl.api

import com.MyApp.budgetControl.api.utils.TestsConstants
import net.datafaker.Faker

import static io.restassured.RestAssured.given

class CommonTest extends TestsConstants {

    def faker = new Faker()

    def createCategory(String categoryName) {
        def payload = [categoryName: categoryName]
        return given()
                .contentType(CONTENT_TYPE_JSON)
                .body(payload)
                .when()
                .post(CATEGORIES_PATH)
                .then()
                .statusCode(201)
                .extract().response()
                .body().jsonPath()
                .getString("categoryId")
    }

    def createUser(String userName) {
        def payload = [userName: userName, userEmail: "%s@mail.com".formatted(userName)]
        return given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post(USERS_PATH)
                .then()
                .statusCode(201)
                .extract().response()
                .body().jsonPath()
                .getString("userId")
    }

    def createExpense(String categoryId, String userId) {
        def payload = [expenseCost: EXPENSE_COST, categoryId: categoryId, expenseComment: "Comment prepared expense", userId: userId]
        return given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post(EXPENSES_PATH)
                .then()
                .statusCode(201)
                .extract().response()
                .body().jsonPath()
                .getString("expenseId")

    }

    def getUserIdByUserName(String userName) {
        return given()
                .when().get(USERS_PATH)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("find {it.userName == '${userName}'}.userId")
    }

    def getCategoryIdByCategoryName(String categoryName) {
        return given()
                .when().get(CATEGORIES_PATH)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("find {it.categoryName == '${categoryName}'}.categoryId")
    }

}
