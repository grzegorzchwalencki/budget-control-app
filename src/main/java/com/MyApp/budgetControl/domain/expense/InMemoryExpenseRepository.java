package com.MyApp.budgetControl.domain.expense;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

abstract class InMemoryExpenseRepository implements ExpenseRepository {
  Instant date = new Date().toInstant();

  private final List<ExpenseEntity> expenses = new ArrayList<>(Arrays.asList(
     new ExpenseEntity(UUID.randomUUID().toString(), 49.99, "groceries", "Biedronka market", date),
     new ExpenseEntity(UUID.randomUUID().toString(), 10.00, "eating out ", "chinese food", date),
     new ExpenseEntity(UUID.randomUUID().toString(), 150.50, "transport ", "Orlen", date)
  ));

  @Override
  public final List<ExpenseEntity> findAll() {
    return  expenses;
  }

  @Override
  public ExpenseEntity save(ExpenseEntity newExpense) {
    expenses.add(newExpense);
    return  newExpense;
  }
}
