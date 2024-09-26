package com.MyApp.budgetControl.domain.expense;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
class InMemoryExpenseRepository implements ExpenseRepository {
    private final List<Expense> expenses = new ArrayList<>(Arrays.asList(
            new Expense(101, 49.99, "groceries", "Biedronka market", "07.09.2024"),
            new Expense(102, 10.00, "eating out ", "chinese food", "09.09.2024"),
            new Expense(103, 150.50, "transport ", "Orlen", "11.09.2024")
    ));
    @Override
    public final List<Expense> getExpensesFromRepository() {
        return  expenses;
    }

    @Override
    public void addNewExpenseToRepository(Expense newExpense) {
        expenses.add(newExpense);
    }
}
