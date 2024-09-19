package com.MyApp.budgetControl.controller;

import com.MyApp.budgetControl.model.Expense;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpensesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getExpensesMethodShouldReturnCode200andAppJsonContentType() throws Exception {
        mockMvc.perform(get("/expenses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getExpenseByIdMethodShouldReturnCode200andAppJsonContentType() throws Exception {
        mockMvc.perform(get("/expenses/101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"expenseId\":101,\"expenseCost\":49.99,\"expenseCategory\":\"groceries\",\"expenseComment\":\"Biedronka market\",\"expenseDate\":\"07.09.2024\"}"));
    }

    @Test
    void getExpenseByIdMethodForNotExistingIdShouldReturnCode404() throws Exception {
        mockMvc.perform(get("/expenses/100"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/problem+json"));
    }

    @Test
    void postNewExpenseWithAllFieldsCorrectShouldAddToRepositoryAndReturnStatusCreated() throws Exception {
        Expense newExpense = new Expense(
                104,
                99.00,
                "test category",
                "test comment",
                "17.09.2024");
        mockMvc.perform(post("/expenses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newExpense)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"expenseId\":104,\"expenseCost\":99.00,\"expenseCategory\":\"test category\",\"expenseComment\":\"test comment\",\"expenseDate\":\"17.09.2024\"}"));
    }

}