package com.MyApp.budgetControl.model;

import lombok.Value;

@Value
public class Expense {

    private final int expenseId;
    private final double expenseCost;
    private final String expenseCategory;
    private final String expenseComment;
    private final String expenseDate;

}
