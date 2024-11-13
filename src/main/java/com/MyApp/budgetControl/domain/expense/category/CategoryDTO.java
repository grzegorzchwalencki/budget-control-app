package com.MyApp.budgetControl.domain.expense.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CategoryDTO {

  private final Long id;

  @NotBlank(message = "Category name is mandatory")
  @Size(max = 64, message = "Category name max length is 64 char")
  @JsonProperty("categoryName")
  private final String categoryName;

}
