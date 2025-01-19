package com.MyApp.budgetControl.domain;

import com.MyApp.budgetControl.domain.expense.ExpenseRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceOrchestratorTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  private static final String regex = "[\"\\[\\]]";
  protected String userId;
  protected String categoryId;


  @BeforeAll
  @SneakyThrows
  void setUp() {
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
  }

  @Test
  @SneakyThrows
  public void addNewExpenseAndUserShouldHasThatExpenseInUserExpensesCollection() {
    mockMvc.perform(post("/expenses")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(
            new ExpenseRequestDTO(123, categoryId, "initial-test-comment", userId))));
    String newExpense =  mockMvc.perform(get("/expenses"))
        .andReturn().getResponse().getContentAsString();
    String newExpenseId = JsonPath.read(
        newExpense, "$[*].expenseId").toString().replaceAll(regex, "");

    mockMvc.perform(get("/users/" + userId))
        .andExpect(jsonPath("userExpenses[0].expenseId").value(newExpenseId));
  }
}
