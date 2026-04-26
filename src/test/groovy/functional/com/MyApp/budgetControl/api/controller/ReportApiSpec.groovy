package com.MyApp.budgetControl.api.controller

import com.MyApp.budgetControl.api.CommonTest

import static io.restassured.RestAssured.get

class ReportApiSpec extends CommonTest {

    def "get monthlyReport for user who has any expenses in current month should return report with expected values"() {
        given:
            def categoryName = faker.app().name()
            def userName = faker.ancient().hero()

            def userId = createUser(userName)
            def categoryId = createCategory(categoryName)

            createExpense(categoryId, userId)
            createExpense(categoryId, userId)

            def expectedTotal = (EXPENSE_COST * 2).toString()

        when:
            def response = get("/reports/users/%s/expenses/monthly-report".formatted(userId))
            def body = response.jsonPath()

        then:
            response.statusCode() == 200
            response.contentType() == CONTENT_TYPE_JSON

        and:
            with(body) {
                getString("userName") == userName
                getString("monthlyExpensesTotal").contains(expectedTotal)
                getString("categories.name").contains(categoryName)
                getString("categories.total").contains(expectedTotal)
            }
    }

    def "get monthlyReport for user who has no expenses should return report with expected values"() {
        given:
            def userName = faker.ancient().hero()
            def userId = createUser(userName)

        when:
            def response = get("/reports/users/%s/expenses/monthly-report".formatted(userId))
            def body = response.jsonPath()

        then:
            response.statusCode() == 200
            response.contentType() == CONTENT_TYPE_JSON

        and:
            with(body) {
                getString("userName") == userName
                getString("monthlyExpensesTotal") == null
                getList("categories").isEmpty()
            }
    }

}
