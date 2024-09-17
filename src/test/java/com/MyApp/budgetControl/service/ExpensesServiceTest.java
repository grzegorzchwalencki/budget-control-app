package com.MyApp.budgetControl.service;

import com.MyApp.budgetControl.model.Expense;
import com.MyApp.budgetControl.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ExpensesServiceTest {

    @Mock
    ExpenseRepository mockRepository;
    @BeforeEach
    public void setup() {
        when(mockRepository.getExpensesFromRepository()).thenReturn(Arrays.asList(
                new Expense(101, 49.99, "groceries","Biedronka market", "07.09.2024"),
                new Expense(102, 10.00, "eating out ", "chinese food", "09.09.2024")));
    }

    @Test
    void getExpensesShouldReturnAtLeastOneExpanse() {
        var service = new ExpensesService(mockRepository);
        List<Expense> expanses = service.getExpenses();
        Expense expected = new Expense(
                101,
                49.99,
                "groceries",
                "Biedronka market",
                "07.09.2024");
        assertTrue(expanses.contains(expected));
    }

    @Test
    void getExpenseByIdForExistingIdIsReturningCorrectExpense() {
        var service = new ExpensesService(mockRepository);
        Expense result = service.getExpenseById(102);
        Expense expected = new Expense(
                102,
                10.00,
                "eating out ",
                "chinese food",
                "09.09.2024");
        assertEquals(expected, result);
    }

    @Test
    void getExpenseByIdFoNotExistingIdShouldThrowException() {
        var service = new ExpensesService(mockRepository);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                    service.getExpenseById(1));
            assertEquals("404 NOT_FOUND \"Expense with Id: 1 Not Found\"", exception.getMessage());
        }
}