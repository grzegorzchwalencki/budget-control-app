package com.MyApp.budgetControl.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@TestConfiguration
@Testcontainers
class TestContainersConfiguration {

  @Container
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>
      ("postgres:17.2-alpine3.21");

  static {
    System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
    System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
    }

}
