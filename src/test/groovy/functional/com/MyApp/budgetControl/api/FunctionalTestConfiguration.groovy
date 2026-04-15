package com.MyApp.budgetControl.api

import io.restassured.RestAssured
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
abstract class FunctionalTestConfiguration extends Specification {

    @LocalServerPort
    protected int port

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>
            ("postgres:17.2-alpine3.21")

    static {
        postgres.start()
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl)
        registry.add("spring.datasource.username", postgres::getUsername)
        registry.add("spring.datasource.password", postgres::getPassword)
    }

    def setup() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = this.port
    }

}
