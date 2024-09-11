package com.MyApp.budgetControl.controller;

import com.MyApp.budgetControl.model.Expense;
import com.MyApp.budgetControl.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpensesController {

    @Autowired
    ExpenseService expenseService;
    @RequestMapping("/expenses")
    public List<Expense> getExpenses () {
        return expenseService.getExpenses();
}
    @RequestMapping("/expenses/{expenseId}")
    public Expense getExpenseById (@PathVariable int expenseId) {
        return expenseService.getExpenceById(expenseId);
        }
}
