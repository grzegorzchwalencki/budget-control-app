package com.MyApp.budgetControl.api

import static io.restassured.RestAssured.get

class OpenApiDocsGenerationTest extends FunctionalTestConfiguration {

    def "should expose OpenApi Docs"() throws Exception {
        when:
        def response = get("/v3/api-docs")

        then:
            with(response) {
                statusCode() == 200
                body().asString().contains('"OpenAPI definition"')
            }
    }
}
