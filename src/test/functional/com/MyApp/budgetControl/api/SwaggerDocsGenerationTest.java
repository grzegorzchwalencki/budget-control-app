package com.MyApp.budgetControl.api;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerDocsGenerationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldExposeSwaggerDocs() throws Exception {
      mockMvc.perform(get("/v3/api-docs"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("\"OpenAPI definition\"")));
    }
}