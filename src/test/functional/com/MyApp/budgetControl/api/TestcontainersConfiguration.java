package com.MyApp.budgetControl.api;

import org.junit.jupiter.api.AfterAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

abstract class TestContainersConfiguration {

  private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>
      ("postgres:17.2-alpine3.21");

  static {
    postgres.start();

  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }
}
