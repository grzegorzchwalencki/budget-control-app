package com.MyApp.budgetControl.repository;

import com.MyApp.budgetControl.model.Expense;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ExpenseRepository {
    final List<Expense> expenses = new ArrayList<>(Arrays.asList(
            new Expense(101, 49.99, "groceries", "Biedronka market", "07.09.2024"),
            new Expense(102, 10.00, "eating out ", "chinese food", "09.09.2024"),
            new Expense(103, 150.50, "transport ", "Orlen", "11.09.2024")
    ));
    public final List<Expense> getExpensesFromRepository() {
        return  expenses;
    }

    public void addNewExpenseToRepository(Expense newExpense) {
        expenses.add(newExpense);
    }
}
