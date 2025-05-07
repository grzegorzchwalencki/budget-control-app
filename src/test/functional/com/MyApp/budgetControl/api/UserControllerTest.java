package com.MyApp.budgetControl.api;

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
import lombok.SneakyThrows;
import net.datafaker.Faker;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class UserControllerTest extends TestContainersConfiguration{

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  Faker faker = new Faker();

  public String generateUser(String userName) {
    return  String.format("""
      {"userName":"%s",
      "userEmail":"%s"}""", userName, faker.internet().emailAddress());
  }

    private static final String regex = "[\"\\[\\]]";

  @Test
  @SneakyThrows
  void getUserMethodShouldReturnAllExistingRecordsCode200andAppJsonContentType() {
    String userName1 = faker.name().firstName();
    String userName2 = faker.name().firstName();
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(generateUser(userName1)));
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(generateUser(userName2)));
    mockMvc.perform(get("/users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[*].userName", hasItem(userName1)))
        .andExpect(jsonPath("$[*].userName", hasItem(userName2)));
  }

  @Test
  @SneakyThrows
  void getUserByIdMethodShouldReturnCode200andAppJsonContentTypeWithCorrectJsonContent() {
    String userName = faker.name().firstName();
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(generateUser(userName)));

    String id = findUserIdByName(userName);

    mockMvc.perform(get("/users/" + id))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.userName").value(userName));
  }

  @Test
  @SneakyThrows
  void getUserByIdMethodForNotExistingIdShouldReturnCode404HandlingNoSuchElementException() {
    mockMvc.perform(get("/users/not-existing-user-id"))
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
    String userName = faker.name().lastName();
    mockMvc.perform(post("/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content(generateUser(userName)))
           .andExpect(status().isCreated());
    mockMvc.perform(get("/users"))
        .andExpect(jsonPath("$[*].userName", hasItem(userName)));
  }

  @Test
  @SneakyThrows
  void postNewUserWithAlreadyUsedUserNameShouldThrowAnConflictError() {
    String userName = faker.name().lastName();
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(generateUser(userName)))
        .andExpect(status().isCreated());
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(generateUser(userName)))
        .andExpect(status().isConflict())
        .andExpect(content().json("{\"statusCode\":409,"
            + "\"errorDetails\":[\"Name is already used. Please choose a different one\"],"
            + "\"errorType\":\"CONFLICT_ERROR\"}"));
  }

  @Test
  @SneakyThrows
  void deleteUserShouldRemoveItFromRepositoryAndReturnStatusAccepted() {
    String userName = faker.name().name();
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(generateUser(userName)));

    String id = findUserIdByName(userName);

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
    mockMvc.perform(delete("/users/not-existing-user-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorDetails").value("Element with given Id does not exist"));
  }

  @SneakyThrows
  private String findUserIdByName(String userName) {
    String result =  mockMvc.perform(get("/users")).andReturn().getResponse().getContentAsString();
    String[] usersInBase = JsonPath.read(result, "$[*].userName").toString().replaceAll(regex, "").split(",");
    int index = Arrays.asList(usersInBase).indexOf(userName);
    return JsonPath
        .read(result, "$[*].userId")
        .toString()
        .replaceAll(regex, "")
        .split(",")[index];
  }
}