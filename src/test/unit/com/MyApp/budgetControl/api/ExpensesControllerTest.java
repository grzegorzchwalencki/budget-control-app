package com.MyApp.budgetControl.api;

import com.MyApp.budgetControl.domain.expense.ExpenseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/initial-data.sql")
class ExpensesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @SneakyThrows
    void getExpensesMethodShouldReturnCode200andAppJsonContentType() {
        mockMvc.perform(get("/expenses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[*].expenseCategory")
                        .value(containsInAnyOrder("test category 1", "test category 2", "test category 3", "test category 4")));
    }

    @Test
    @SneakyThrows
    void getExpenseByIdMethodShouldReturnCode200andAppJsonContentType() {
        mockMvc.perform(get("/expenses/caf8b686-b9b6-40ef-bdb8-e75a7911164b"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"expenseCost\":999.0,\"expenseCategory\":\"test category 2\",\"expenseComment\":\"test comment 2\"}"));
    }

    @Test
    @SneakyThrows
    void getExpenseByIdMethodForNotExistingIdShouldReturnCode404() {
        mockMvc.perform(get("/expenses/1f76ff4a-d3e6-479e-b74f-8628c4d5adc9"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @SneakyThrows
    void postNewExpenseWithAllFieldsCorrectShouldAddToRepositoryAndReturnStatusCreated() {
        mockMvc.perform(post("/expenses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"expenseCost\":99.0,\"expenseCategory\":\"test category\",\"expenseComment\":\"test comment\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"expenseCost\":99.00,\"expenseCategory\":\"test category\",\"expenseComment\":\"test comment\"}"));
    }

    @SneakyThrows
    @ParameterizedTest
    @CsvFileSource(resources = "/postTestData.csv", numLinesToSkip = 1)
    void postNewExpenseWithInvalidFieldsShouldReturnErrorResponse(@RequestBody int expenseCost, String expenseCategory, String expenseComment, String expectedMessage) {
        ExpenseEntity newExpense = new ExpenseEntity(
                null,
                expenseCost,
                expenseCategory,
                expenseComment,
                null );
        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newExpense)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorDetails").value(expectedMessage));
    }

}