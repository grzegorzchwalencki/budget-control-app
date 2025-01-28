package com.MyApp.budgetControl.domain.expense.dto;

import com.MyApp.budgetControl.domain.expense.ExpenseEntity;
import lombok.Value;

import java.time.Instant;

@Value
public class ExpenseResponseDTO {

  public ExpenseResponseDTO(ExpenseEntity expense) {
    this.expenseId = expense.getExpenseId();
    this.expenseCost = expense.getExpenseCost();
    this.expenseCategory = expense.getExpenseCategory().getCategoryName();
    this.expenseComment = expense.getExpenseComment();
    this.expenseDate = expense.getExpenseDate();
    this.userName = expense.getUser().getUserName();
  }

  private final String expenseId;
  private final double expenseCost;
  private final String expenseCategory;
  private final String expenseComment;
  private final Instant expenseDate;
  private final String userName;

}
