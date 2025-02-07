package com.MyApp.budgetControl.domain.expense;

import java.util.List;
import java.util.Optional;

interface ExpenseRepository {

  ExpenseEntity save(ExpenseEntity newExpense);

  List<ExpenseEntity> findAll();

  Optional<ExpenseEntity> findById(String expenseId);

  void deleteById(String expenseId);
}