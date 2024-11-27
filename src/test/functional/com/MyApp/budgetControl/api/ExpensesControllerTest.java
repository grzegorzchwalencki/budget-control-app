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

import com.MyApp.budgetControl.domain.expense.ExpenseRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;


@SpringBootTest
@AutoConfigureMockMvc
@Sql("/initial-data-expenses.sql")
class ExpensesControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @SneakyThrows
  void getExpensesMethodShouldReturnAllExistingRecordsCode200andAppJsonContentType() {
    mockMvc.perform(get("/expenses"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$[*].expenseCategory")
           .value(containsInAnyOrder("test category 1", "test category 2",
               "test category 3", "test category 4")));
  }

  @Test
  @SneakyThrows
  void requestCausingToUnhandledErrorShouldReturnErrorResponseCatchUnhandledError() {
    mockMvc.perform(get("/expensesnotvalidendpoint"))
           .andDo(print())
           .andExpect(status().isInternalServerError())
           .andExpect(content().contentType("application/json"))
           .andExpect(content().json(
           "{\"statusCode\":500,"
               + "\"errorDetails\":[\"Unknown error occured\","
               + "\"org.springframework.web.servlet.resource.NoResourceFoundException\","
               + "\"No static resource expensesnotvalidendpoint.\"],"
               + "\"errorType\":\"UNHANDLED_ERROR\"}"));
  }

  @Test
  @SneakyThrows
  void getExpenseByIdMethodShouldReturnCode200andAppJsonContentTypeWithCorrectJsonContent() {
    mockMvc.perform(get("/expenses/caf8b686-b9b6-40ef-bdb8-e75a7911164b"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json"))
           .andExpect(content().json("{\"expenseCost\":999.0,"
               + "\"expenseCategory\":\"test category 2\",\"expenseComment\":\"test comment 2\"}"));
  }

  @Test
  @SneakyThrows
  void getExpenseByIdMethodForNotExistingIdShouldReturnCode404HandlingNoSuchElementException() {
    mockMvc.perform(get("/expenses/1f76ff4a-d3e6-479e-b74f-8628c4d5adc9"))
           .andDo(print())
           .andExpect(status().isNotFound())
           .andExpect(content().contentType("application/json"))
           .andExpect(content().json("{\"statusCode\":404,"
                  + "\"errorDetails\":[\"Expense with given Id does not exist\"],"
                  + "\"errorType\":\"NOT_FOUND_ERROR\"}"));
  }

  @Test
  @SneakyThrows
  void postNewExpenseWithAllFieldsCorrectShouldAddToRepositoryAndReturnStatusCreated() {
    String random_expenseComment = UUID.randomUUID().toString();
    mockMvc.perform(post("/expenses")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"expenseCost\":99.0,\"expenseCategory\":\"test category\","
                   + "\"expenseComment\":\"" + random_expenseComment + "\"}"))
           .andExpect(status().isCreated());
    mockMvc.perform(get("/expenses"))
        .andExpect(jsonPath("$[*].expenseComment", hasItem(random_expenseComment)));

  }

  @SneakyThrows
  @ParameterizedTest
  @CsvFileSource(resources = "/postTestData.csv", numLinesToSkip = 1)
  void postNewExpenseWithInvalidFieldsShouldReturnErrorResponseHandlingMethodArgumentNotValidException(
      @RequestBody int expenseCost, String expenseCategory, String expenseComment, String expectedMessage) {
    ExpenseRequestDTO newExpense = new ExpenseRequestDTO(
              expenseCost,
              expenseCategory,
              expenseComment
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
  void deleteExpenseShouldRemoveItFromRepositoryAndReturnStatusAccepted() {
    mockMvc.perform(delete("/expenses/ff4d7eba-f503-4d6a-8e29-10cc1f4ca56d")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isAccepted());
    mockMvc.perform(get("/expenses/ff4d7eba-f503-4d6a-8e29-10cc1f4ca56d")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorDetails")
            .value("Expense with given Id does not exist"));
  }

  @Test
  @SneakyThrows
  void deleteExpenseThatNotExistShouldThrowAnExceptionAndReturnStatusNotFound() {
    mockMvc.perform(delete("/expenses/ff4d7eba-f503-4d6a-8e29-11cc11111111")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorDetails").value("Expense with given Id does not exist"));
  }
}