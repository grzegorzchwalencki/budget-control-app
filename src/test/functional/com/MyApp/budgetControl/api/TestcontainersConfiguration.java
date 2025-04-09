package com.MyApp.budgetControl.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
class TestContainersConfiguration {

  private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>
      ("postgres:17.2-alpine3.21");

  static {
    postgreSQLContainer.start();
    System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
    System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
    }

}
