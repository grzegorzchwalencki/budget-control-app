package com.MyApp.budgetControl.domain.expense.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class CategoryDTO {

  private final Long id;

  @NotBlank(message = "Category name is mandatory")
  @JsonProperty("categoryName")
  private final String categoryName;

}
