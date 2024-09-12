package com.MyApp.budgetControl.controller;

import com.MyApp.budgetControl.model.Expense;
import com.MyApp.budgetControl.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpensesController {

    private final ExpenseService expenseService;

    @GetMapping
    public List<Expense> getExpenses() {
        return expenseService.getExpenses();
    }

    @GetMapping("/{expenseId}")
    public Expense getExpenseById(@PathVariable int expenseId) {
        try {
            return expenseService.getExpenseById(expenseId);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense Not Found", ex);
        }
    }
}