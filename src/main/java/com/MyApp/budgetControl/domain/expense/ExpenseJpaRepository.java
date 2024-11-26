package com.MyApp.budgetControl.domain.expense;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ExpenseJpaRepository extends ExpenseRepository, JpaRepository<ExpenseEntity, String> {
  ExpenseEntity save(ExpenseEntity newExpense);

  List<ExpenseEntity> findAll();

  Optional<ExpenseEntity> findByExpenseId(String expenseId);

  void deleteByExpenseId(String expenseId);
}
