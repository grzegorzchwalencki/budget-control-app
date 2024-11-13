package com.MyApp.budgetControl.domain.expense;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface ExpenseRepository {

  ExpenseEntity save(ExpenseEntity newExpense);

  List<ExpenseEntity> findAll();

  Optional<ExpenseEntity> findByExpenseId(UUID expenseId);
}