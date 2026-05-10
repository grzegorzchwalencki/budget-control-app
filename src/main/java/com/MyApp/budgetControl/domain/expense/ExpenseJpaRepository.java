package com.MyApp.budgetControl.domain.expense;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ExpenseJpaRepository extends ExpenseRepository, JpaRepository<ExpenseEntity, UUID> {
  ExpenseEntity save(ExpenseEntity newExpense);

  List<ExpenseEntity> findAll();

  Optional<ExpenseEntity> findById(UUID expenseId);

  void deleteById(UUID expenseId);
}
