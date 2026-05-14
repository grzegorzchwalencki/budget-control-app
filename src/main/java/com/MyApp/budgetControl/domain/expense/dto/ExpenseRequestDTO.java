package com.MyApp.budgetControl.domain.expense.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseRequestDTO(
    @NotNull(message = "Cost value is mandatory")
    @DecimalMin(value = "0.01", message = "Cost value should be positive")
    @JsonProperty("expenseCost")
    BigDecimal expenseCost,

    @NotNull(message = "Category is mandatory")
    @JsonProperty("categoryId")
    UUID categoryId,

    @NotBlank(message = "Comment is mandatory")
    @Size(max = 128, message = "Comment max length is 128 char")
    @JsonProperty("expenseComment")
    String expenseComment,

    @NotNull(message = "User id is mandatory")
    @JsonProperty("userId")
    UUID userId) {
}
