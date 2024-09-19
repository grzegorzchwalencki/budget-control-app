package com.MyApp.budgetControl.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class Expense {

    @NotNull
    @Min(101)
    private final long expenseId;

    @NotNull(message = "Cost value is mandatory")
    @DecimalMin(value = "0.01", message = "Cost value should be positive")
    private final double expenseCost;

    @NotBlank(message = "Category is mandatory")
    @Size(max=64, message = "Category max length is 64 char")
    private final String expenseCategory;

    @Size(max = 128, message = "Comment max length is 128 char")
    @NotBlank(message = "Comment is mandatory")
    private final String expenseComment;

    @NotBlank(message = "Date is mandatory")
    private final String expenseDate;

}
