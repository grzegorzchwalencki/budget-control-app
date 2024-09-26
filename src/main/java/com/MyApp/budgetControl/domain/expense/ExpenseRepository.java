package com.MyApp.budgetControl.domain.expense;

import java.util.List;

public interface ExpenseRepository {

    List<Expense> getExpensesFromRepository();

    void addNewExpenseToRepository(Expense newExpense);
}
