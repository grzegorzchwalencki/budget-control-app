package com.MyApp.budgetControl.api;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @Order(1)
  @SneakyThrows
  void getCategoriesMethodShouldReturnAllExistingRecordsCode200andAppJsonContentType() {
    mockMvc.perform(post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"categoryName\":\"category_1\"}"));
    mockMvc.perform(post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"categoryName\":\"category_2\"}"));
    mockMvc.perform(get("/categories"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[*].categoryName")
            .value(containsInAnyOrder("category_1", "category_2")));
  }

  @Test
  @SneakyThrows
  void postNewCategoryShouldAddToRepositoryAndReturnStatusCreated() {
    mockMvc.perform(post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"categoryName\":\"shopping\"}"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().json("{\"id\":3,\"categoryName\":\"shopping\"}"));
  }

  @Test
  @SneakyThrows
  void postNewCategoryWithoutInvalidFieldsShouldReturnErrorResponseHandlingMethodArgumentNotValidException() {
    mockMvc.perform(post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"categoryName\": \" \" }"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorDetails").value("Category name is mandatory"));
  }

  @Test
  @SneakyThrows
  void postNewCategoryWithTooLongCategoryNameShouldReturnErrorResponseHandlingMethodArgumentNotValidException() {
    mockMvc.perform(post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"categoryName\":\"TheNameOfCategoryIsLongerThan64TheNameOfCategoryIsLongerThan64aaa\"}"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorDetails").value("Category name max length is 64 char"));
  }
}
