package com.MyApp.budgetControl.domain.expense;

import java.time.Instant;
import lombok.Value;

@Value
public class ExpenseResponseDTO {

  public ExpenseResponseDTO(ExpenseEntity expense) {
    this.expenseId = expense.getExpenseId();
    this.expenseCost = expense.getExpenseCost();
    this.expenseCategory = expense.getExpenseCategory();
    this.expenseComment = expense.getExpenseComment();
    this.expenseDate = expense.getExpenseDate();
  }

  private final String expenseId;
  private final double expenseCost;
  private final String expenseCategory;
  private final String expenseComment;
  private final Instant expenseDate;

}
