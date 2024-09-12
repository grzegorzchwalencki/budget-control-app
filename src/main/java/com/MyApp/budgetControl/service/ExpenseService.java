package com.MyApp.budgetControl.service;

import com.MyApp.budgetControl.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private static final List<Expense> expenses = Arrays.asList(
            new Expense(101, 49.99, "groceries","Biedronka market", "07.09.2024"),
            new Expense(102, 10.00, "eating out ","chinese food", "09.09.2024"),
            new Expense(103, 150.50, "transport ","Orlen", "11.09.2024")
            );

    public List<Expense> getExpenses() {return expenses;}

    public Expense getExpenseById(int expenseId) {
        return expenses.stream()
                .filter(e -> e.getExpenseId() == expenseId)
                .findFirst().get();
    }
}
