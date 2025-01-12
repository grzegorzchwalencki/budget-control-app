//package com.MyApp.budgetControl.domain.expense;
//
//import com.MyApp.budgetControl.domain.category.CategoryEntity;
//import com.MyApp.budgetControl.domain.user.UserEntity;
//
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.UUID;
//
//abstract class InMemoryExpenseRepository implements ExpenseRepository {
//  Instant date = new Date().toInstant();
//  static UserEntity user = new UserEntity(UUID.randomUUID().toString(), "testUserName", "testUser@email.com", Collections.emptySet());
//  static CategoryEntity category = new CategoryEntity(UUID.randomUUID().toString(), "testCategory");
//  private final List<ExpenseEntity> expenses = new ArrayList<>(Arrays.asList(
//     new ExpenseEntity(UUID.randomUUID().toString(), 49.99, category, "Biedronka market", date, user),
//     new ExpenseEntity(UUID.randomUUID().toString(), 10.00, category, "chinese food", date, user),
//     new ExpenseEntity(UUID.randomUUID().toString(), 150.50, category, "Orlen", date, user)
//  ));
//
//  @Override
//  public final List<ExpenseEntity> findAll() {
//    return  expenses;
//  }
//
//  @Override
//  public ExpenseEntity save(ExpenseEntity newExpense) {
//    expenses.add(newExpense);
//    return  newExpense;
//  }
//}
