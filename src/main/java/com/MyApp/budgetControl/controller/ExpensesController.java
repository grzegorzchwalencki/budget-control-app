package com.MyApp.budgetControl.controller;

import com.MyApp.budgetControl.model.Expense;
import com.MyApp.budgetControl.service.ExpenseService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
            return expenseService.getExpenseById(expenseId);
    }
}