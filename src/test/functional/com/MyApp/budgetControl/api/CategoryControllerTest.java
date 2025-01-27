package com.MyApp.budgetControl.api;

import lombok.SneakyThrows;
import net.datafaker.Faker;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;
  Faker faker = new Faker();

  @Test
  @SneakyThrows
  void getCategoriesMethodShouldReturnAllExistingRecordsCode200andAppJsonContentType() {
    String category1 = faker.brand().car();
    String category2 = faker.mountain().name();
    mockMvc.perform(post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"categoryName\":\"" + category1 + "\"}"));
    mockMvc.perform(post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"categoryName\":\"" + category2 + "\"}"));
    mockMvc.perform(get("/categories"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[*].categoryName", hasItem(category1)))
        .andExpect(jsonPath("$[*].categoryName", hasItem(category2)));
  }

  @Test
  @SneakyThrows
  void postNewCategoryShouldAddToRepositoryAndReturnStatusCreated() {
    String category = faker.witcher().potion();
    mockMvc.perform(post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"categoryName\":\"" + category + "\"}"))
        .andDo(print())
        .andExpect(status().isCreated());
    mockMvc.perform(get("/categories"))
        .andExpect(jsonPath("$[*].categoryName", hasItem(category)));
  }

  @Test
  @SneakyThrows
  void postNewCategoryWithCategoryNameEmptyShouldReturnErrorResponseHandlingMethodArgumentNotValidException() {
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
