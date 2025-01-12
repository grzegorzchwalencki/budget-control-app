package com.MyApp.budgetControl.api;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.UUID;
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
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;


  @Test
  @Order(1)
  @SneakyThrows
  void getUserMethodShouldReturnAllExistingRecordsCode200andAppJsonContentType() {
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"userName\":\"userName1\", \"userEmail\":\"userEmail1@test.com\"}"));
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"userName\":\"userName2\", \"userEmail\":\"userEmail2@test.com\"}"));
    mockMvc.perform(get("/users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[*].userName")
            .value(containsInAnyOrder("userName1", "userName2")));
  }

  @Test
  @SneakyThrows
  void getUserByIdMethodShouldReturnCode200andAppJsonContentTypeWithCorrectJsonContent() {
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"userName\":\"userName1\", \"userEmail\":\"userEmail1@test.com\"}"));

    String result =  mockMvc.perform(get("/users")).andReturn().getResponse().getContentAsString()
        .replace("[", "").replace("]", "");
    String id = JsonPath.read(result, "$.userId");

    mockMvc.perform(get("/users/" + id))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json"))
           .andExpect(content().json("{\"userId\":" + id
               + ",\"userName\":\"userName1\",\"userEmail\":\"userEmail1@test.com\"}"));
  }

  @Test
  @SneakyThrows
  void getUserByIdMethodForNotExistingIdShouldReturnCode404HandlingNoSuchElementException() {
    mockMvc.perform(get("/users/1f76ff4a-d3e6-479e-b74f-ffffffffffff"))
           .andDo(print())
           .andExpect(status().isNotFound())
           .andExpect(content().contentType("application/json"))
           .andExpect(content().json("{\"statusCode\":404,"
                  + "\"errorDetails\":[\"Element with given Id does not exist\"],"
                  + "\"errorType\":\"NOT_FOUND_ERROR\"}"));
  }

  @Test
  @SneakyThrows
  void postNewUserWithAllFieldsCorrectShouldAddToRepositoryAndReturnStatusCreated() {
    String randomUserName = UUID.randomUUID().toString();
    mockMvc.perform(post("/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"userName\":\"" + randomUserName + "\","
                   + "\"userEmail\":\"user@address.com\"}"))
           .andExpect(status().isCreated());
    mockMvc.perform(get("/users"))
        .andExpect(jsonPath("$[*].userName", hasItem(randomUserName)));
  }

  @Test
  @SneakyThrows
  void postNewUserWithAlreadyUsedUserNameShouldThrowAnConflictError() {
    String randomUserName = UUID.randomUUID().toString();
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userName\":\"" + randomUserName + "\","
                + "\"userEmail\":\"user@address.com\"}"))
        .andExpect(status().isCreated());
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userName\":\"" + randomUserName + "\","
                + "\"userEmail\":\"user@address2.com\"}"))
        .andExpect(status().isConflict())
        .andExpect(content().json("{\"statusCode\":409,"
            + "\"errorDetails\":[\"Name is already used. Please choose a different one.\"],"
            + "\"errorType\":\"CONFLICT_ERROR\"}"));
  }

  @Test
  @SneakyThrows
  void deleteUserShouldRemoveItFromRepositoryAndReturnStatusAccepted() {
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"userName\":\"userName1\", \"userEmail\":\"userEmail1@test.com\"}"));

    String result =  mockMvc.perform(get("/users")).andReturn().getResponse().getContentAsString()
        .replace("[", "").replace("]", "");
    String id = JsonPath.read(result, "$.userId");

    mockMvc.perform(delete("/users/" + id)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isAccepted());
    mockMvc.perform(get("/users/" + id)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorDetails")
            .value("Element with given Id does not exist"));
  }

  @Test
  @SneakyThrows
  void deleteUserThatNotExistShouldThrowAnExceptionAndReturnStatusNotFound() {
    mockMvc.perform(delete("/users/ff4d7eba-f503-4d6a-8e29-ff1ff1ff1ff")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorDetails").value("Element with given Id does not exist"));
  }
}