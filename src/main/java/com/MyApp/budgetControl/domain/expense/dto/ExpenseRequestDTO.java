package com.MyApp.budgetControl.domain.expense.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Value;

@Value
public class ExpenseRequestDTO {

  @NotNull(message = "Cost value is mandatory")
  @DecimalMin(value = "0.01", message = "Cost value should be positive")
  @JsonProperty("expenseCost")
  private final BigDecimal expenseCost;

  @NotBlank(message = "Category is mandatory")
  @Size(max = 36)
  @JsonProperty("categoryId")
  private final String categoryId;

  @NotBlank(message = "Comment is mandatory")
  @Size(max = 128, message = "Comment max length is 128 char")
  @JsonProperty("expenseComment")
  private final String expenseComment;

  @NotBlank(message = "User id is mandatory")
  @Size(max = 36)
  @JsonProperty("userId")
  private final String userId;

}
