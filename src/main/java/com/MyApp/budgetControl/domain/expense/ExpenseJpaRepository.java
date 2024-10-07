package com.MyApp.budgetControl.domain.expense;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseJpaRepository extends ExpenseRepository, JpaRepository<ExpenseEntity, UUID> {
    ExpenseEntity save(ExpenseEntity newExpense);

    List<ExpenseEntity> findAll();

    Optional<ExpenseEntity> findByExpenseId(UUID expenseId);
}
