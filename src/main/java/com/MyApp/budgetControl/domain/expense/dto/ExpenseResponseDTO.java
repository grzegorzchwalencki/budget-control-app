package com.MyApp.budgetControl.domain.expense.dto;

import com.MyApp.budgetControl.domain.expense.ExpenseEntity;
import java.time.Instant;
import lombok.Value;

@Value
public class ExpenseResponseDTO {

  public ExpenseResponseDTO(ExpenseEntity expense) {
    this.expenseId = expense.getExpenseId();
    this.expenseCost = expense.getExpenseCost();
    this.categoryId = expense.getCategoryId().getCategoryId();
    this.expenseComment = expense.getExpenseComment();
    this.expenseDate = expense.getExpenseDate();
    this.userId = expense.getUserId().getUserId();
  }

  private final String expenseId;
  private final double expenseCost;
  private final String categoryId;
  private final String expenseComment;
  private final Instant expenseDate;
  private final String userId;

}
