package com.MyApp.budgetControl.api;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.UUID;
import lombok.SneakyThrows;
import net.datafaker.Faker;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpensesControllerTest extends TestContainersConfiguration {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  private static final String regex = "[\"\\[\\]]";
  protected String initUserId;
  protected String initCategoryId;
  protected String initExpenseId;
  private static final String notFoundMsg = "Element with given Id does not exist";
  Faker faker = new Faker();
  String userName = faker.rickAndMorty().character();
  String categoryName = faker.tea().type();
  String comment = faker.friends().location();

  @SneakyThrows
  @BeforeAll
  public void setUp() {
    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"userName\":\"" + userName + "\",\"userEmail\":\"test@test.com\"}"));
    initUserId = findIdByAttributeValue(userName, "user");

    mockMvc.perform(post("/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"categoryName\":\"" + categoryName + "\"}"));
    initCategoryId = findIdByAttributeValue(categoryName, "category");

    mockMvc.perform(post("/expenses")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(
            new ExpenseRequestDTO(123, initCategoryId, comment, initUserId))));
    initExpenseId = findIdByAttributeValue(comment, "expense");
  }

  @Test
  @SneakyThrows
  void getExpensesMethodShouldReturnAllExistingRecordsCode200andAppJsonContentType() {
    mockMvc.perform(get("/expenses"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[*].expenseComment", hasItem(comment)));
  }

  @Test
  @SneakyThrows
  void getExpenseByIdMethodShouldReturnCode200andAppJsonContentTypeWithCorrectJsonContent() {
    mockMvc.perform(get("/expenses/" + initExpenseId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().json(String.format("""
            {"expenseCost":123.0,
            "categoryId":"%s",
            "expenseComment":"%s"}""", initCategoryId, comment)));
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
    String newComment = faker.beer().style();

    mockMvc.perform(post("/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(
                new ExpenseRequestDTO(123, initCategoryId, newComment, initUserId))))
        .andDo(print())
        .andExpect(status().isCreated());

    String expenseToDeleteId = findIdByAttributeValue(newComment, "expense");

    mockMvc.perform(delete("/expenses/" + expenseToDeleteId)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isAccepted());

    mockMvc.perform(get("/expenses/" + expenseToDeleteId)
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
                new ExpenseRequestDTO(123, initCategoryId, random_expenseComment, initUserId))))
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
        initCategoryId,
        expenseComment,
        initUserId
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

  @SneakyThrows
  private String findIdByAttributeValue(String attributeValue, String typeOfEntity) {
    String urlTemplate;
    String searchAttribute;
    String idAttribute;
    switch (typeOfEntity) {
      case "category":
        urlTemplate = "/categories";
        searchAttribute = "categoryName";
        idAttribute = "categoryId";
        break;
      case "expense":
        urlTemplate = "/expenses";
        searchAttribute = "expenseComment";
        idAttribute = "expenseId";
        break;
      case "user":
        urlTemplate = "/users";
        searchAttribute = "userName";
        idAttribute = "userId";
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + typeOfEntity);
    }
    String result = mockMvc.perform(get(urlTemplate)).andReturn().getResponse().getContentAsString();
    String[] inBase = JsonPath.read(result, "$[*]." + searchAttribute)
        .toString().replaceAll(regex, "").split(",");
    int index = Arrays.asList(inBase).indexOf(attributeValue);

    if (index < 0) {
      return JsonPath.read(result, "$[*]." + idAttribute)
          .toString().replaceAll(regex, "");
    } else {
      return JsonPath.read(result, "$[*]." + idAttribute)
          .toString().replaceAll(regex, "").split(",")[index];
    }
  }
}