package com.MyApp.budgetControl.controller;

import com.MyApp.budgetControl.model.Expense;
import com.MyApp.budgetControl.service.ExpensesService;
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

    private final ExpensesService expensesService;

    @GetMapping
    public List<Expense> getExpenses() {
        return expensesService.getExpenses();
    }

    @GetMapping("/{expenseId}")
    public Expense getExpenseById(@PathVariable int expenseId) {
            return expensesService.getExpenseById(expenseId);
    }
}