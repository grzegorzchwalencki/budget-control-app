package com.MyApp.budgetControl.api;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.MyApp.budgetControl.domain.expense.ExpenseRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpensesControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  private static final String regex = "[\"\\[\\]]";
  protected String userId;
  protected String categoryId;
  protected String initialExpenseId;
  private static final String notFoundMsg = "Element with given Id does not exist";

  @SneakyThrows
  @BeforeAll
  public void setUp() {
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"userName\":\"test01\",\"userEmail\":\"test@test.com\"}"));
    String user =  mockMvc.perform(get("/users"))
        .andReturn().getResponse().getContentAsString();
    userId = JsonPath.read(user, "$[*].userId").toString().replaceAll(regex, "");

    mockMvc.perform(post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"categoryName\":\"testCategory\"}"));
    String category =  mockMvc.perform(get("/categories"))
        .andReturn().getResponse().getContentAsString();
    categoryId = JsonPath.read(category, "$[*].categoryId").toString().replaceAll(regex, "");

    mockMvc.perform(post("/expenses")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(
            new ExpenseRequestDTO(123, categoryId, "initial-test-comment", userId))));
    String initialExpense =  mockMvc.perform(get("/expenses"))
        .andReturn().getResponse().getContentAsString();
    initialExpenseId = JsonPath.read(
        initialExpense, "$[*].expenseId").toString().replaceAll(regex, "");
  }

  @Test
  @SneakyThrows
  void getExpensesMethodShouldReturnAllExistingRecordsCode200andAppJsonContentType() {
    mockMvc.perform(get("/expenses"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$[*].expenseComment", hasItem("initial-test-comment")));
  }

  @Test
  @SneakyThrows
  void getExpenseByIdMethodShouldReturnCode200andAppJsonContentTypeWithCorrectJsonContent() {
    mockMvc.perform(get("/expenses/" + initialExpenseId))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json"))
           .andExpect(content().json("""
               {"expenseCost":123.0,
               "expenseCategory":"testCategory",
               "expenseComment":"initial-test-comment"}"""));
  }

  @Test
  @SneakyThrows
  void getExpenseByIdMethodForNotExistingIdShouldReturnCode404HandlingNoSuchElementException() {
    mockMvc.perform(get("/expenses/not-existing-expense-id"))
           .andDo(print())
           .andExpect(status().isNotFound())
           .andExpect(content().contentType("application/json"))
           .andExpect(content().json("""
                  {"statusCode":404,
                  "errorDetails":["Element with given Id does not exist"],
                  "errorType":"NOT_FOUND_ERROR"}"""));
  }

  @Test
  @SneakyThrows
  void deleteExpenseShouldRemoveItFromRepositoryAndReturnStatusAccepted() {
    mockMvc.perform(post("/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(
                new ExpenseRequestDTO(123, categoryId, "test-comment", userId))))
        .andDo(print())
        .andExpect(status().isCreated());
    String expense =  mockMvc.perform(get("/expenses"))
        .andReturn().getResponse().getContentAsString();
    String expenseId = JsonPath.read(expense, "$[*].expenseId").toString().replaceAll(regex, "").split(",")[1];
    mockMvc.perform(delete("/expenses/" + expenseId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isAccepted());
    mockMvc.perform(get("/expenses/" + expenseId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorDetails")
            .value(notFoundMsg));
  }

  @Test
  @SneakyThrows
  void addNewExpenseShouldReturnStatusCodeCreatedAndSaveExpense() {
    String random_expenseComment = UUID.randomUUID().toString();
    mockMvc.perform(post("/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(
                new ExpenseRequestDTO(123, categoryId, random_expenseComment, userId))))
        .andDo(print())
        .andExpect(status().isCreated());
    mockMvc.perform(get("/expenses"))
        .andExpect(jsonPath("$[*].expenseComment", hasItem(random_expenseComment)));
  }

  @SneakyThrows
  @ParameterizedTest
  @CsvFileSource(resources = "/postTestData.csv", numLinesToSkip = 1)
  void postNewExpenseWithInvalidFieldsShouldReturnErrorResponseHandlingMethodArgumentNotValidException(
      @RequestBody int expenseCost, String expenseComment, String expectedMessage) {
    ExpenseRequestDTO newExpense = new ExpenseRequestDTO(
        expenseCost,
        categoryId,
        expenseComment,
        userId
    );
    mockMvc.perform(post("/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newExpense)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorDetails").value(expectedMessage));
  }

  @Test
  @SneakyThrows
  void deleteExpenseThatNotExistShouldThrowAnExceptionAndReturnStatusNotFound() {
    mockMvc.perform(delete("/expenses/not-existing-expense-id")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorDetails").value(notFoundMsg));
  }

  @Test
  @SneakyThrows
  void requestCausingToUnhandledErrorShouldReturnErrorResponseCatchUnhandledError() {
    mockMvc.perform(get("/expensesnotvalidendpoint"))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().json(
            """
                {"statusCode":500,
                "errorDetails":["Unknown error occured",
                "org.springframework.web.servlet.resource.NoResourceFoundException",
                "No static resource expensesnotvalidendpoint."],
                "errorType":"UNHANDLED_ERROR"}"""));
  }
}