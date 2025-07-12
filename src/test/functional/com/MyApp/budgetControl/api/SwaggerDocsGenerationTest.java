package com.MyApp.budgetControl.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SwaggerDocsGenerationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void swaggerDocsAreGeneratedCorrectly() {
    String url = "http://localhost:" + port + "/v3/api-docs";

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).contains("\"openapi\":\"3.0.1\"");
  }
}