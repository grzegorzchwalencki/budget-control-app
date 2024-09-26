package com.MyApp.budgetControl.api;

import com.MyApp.budgetControl.domain.expense.Expense;
import com.MyApp.budgetControl.domain.expense.ExpensesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

//    @ExceptionHandler(value = ExceptionHandler.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense addNewExpense(@Valid @RequestBody Expense newExpense) {
        expensesService.addNewExpense(newExpense);
        return newExpense;
    }
}