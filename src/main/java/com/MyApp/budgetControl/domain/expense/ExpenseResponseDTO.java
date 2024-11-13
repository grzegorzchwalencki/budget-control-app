package com.MyApp.budgetControl.domain.expense;

import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class ExpenseResponseDTO {

    public ExpenseResponseDTO(ExpenseEntity expense) {
        this.expenseId = expense.getExpenseId();
        this.expenseCost = expense.getExpenseCost();
        this.expenseCategory = expense.getExpenseCategory();
        this.expenseComment = expense.getExpenseComment();
        this.expenseDate = expense.getExpenseDate();
    }

    private final UUID expenseId;
    private final double expenseCost;
    private final String expenseCategory;
    private final String expenseComment;
    private final Instant expenseDate;


}
