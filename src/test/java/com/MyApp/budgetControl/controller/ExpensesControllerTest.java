package com.MyApp.budgetControl.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpensesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getExpensesMethodShouldReturnCode200andAppJsonContentType() throws Exception {
        this.mockMvc.perform(get("/expenses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getExpenseByIdMethodShouldReturnCode200andAppJsonContentType() throws Exception {
        this.mockMvc.perform(get("/expenses/101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getExpenseByIdMethodForNotExisitngIdShouldReturnCode404() throws Exception {
        this.mockMvc.perform(get("/expenses/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}