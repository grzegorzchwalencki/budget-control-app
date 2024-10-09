package com.MyApp.budgetControl.api;

import com.MyApp.budgetControl.domain.expense.ExpenseDTO;
import com.MyApp.budgetControl.domain.expense.ExpenseEntity;
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
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpensesController {

    private final ExpensesService expensesService;

    @GetMapping
    public List<ExpenseEntity> getExpenses() {
        return expensesService.findAllExpenses();
    }

    @GetMapping("/{expenseId}")
    public ExpenseEntity getExpenseById(@PathVariable UUID expenseId) {
            return expensesService.findExpenseById(expenseId);
    }

//    @ExceptionHandler(value = ExceptionHandler.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseEntity addNewExpense(@Valid @RequestBody ExpenseDTO newExpense) {
        ExpenseEntity expense = new ExpenseEntity(newExpense);
        expensesService.saveExpense(expense);
        return expense;
    }
}