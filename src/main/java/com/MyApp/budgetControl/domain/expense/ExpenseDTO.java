package com.MyApp.budgetControl.domain.expense;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class ExpenseDTO {

    @NotNull(message = "Cost value is mandatory")
    @DecimalMin(value = "0.01", message = "Cost value should be positive")
    @JsonProperty("expenseCost")
    private final double expenseCost;

    @NotBlank(message = "Category is mandatory")
    @Size(max=64, message = "Category max length is 64 char")
    @JsonProperty("expenseCategory")
    private final String expenseCategory;

    @NotBlank(message = "Comment is mandatory")
    @Size(max = 128, message = "Comment max length is 128 char")
    @JsonProperty("expenseComment")
    private final String expenseComment;

}