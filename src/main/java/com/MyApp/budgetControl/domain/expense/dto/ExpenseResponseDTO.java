package com.MyApp.budgetControl.domain.expense.dto;

import com.MyApp.budgetControl.domain.expense.ExpenseEntity;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ExpenseResponseDTO(
    UUID expenseId,
    BigDecimal expenseCost,
    UUID categoryId,
    String expenseComment,
    Instant expenseDate,
    UUID userId
) {

  public ExpenseResponseDTO(ExpenseEntity entity) {
    this(
        entity.getExpenseId(),
        entity.getExpenseCost(),
        entity.getCategoryId().getCategoryId(),
        entity.getExpenseComment(),
        entity.getExpenseDate(),
        entity.getUserId().getUserId()
    );
  }

}
