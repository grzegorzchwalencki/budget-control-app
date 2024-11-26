package com.MyApp.budgetControl.domain.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CategoryRequestDTO {

  @NotBlank(message = "Category name is mandatory")
  @Size(max = 64, message = "Category name max length is 64 char")
  @JsonProperty("categoryName")
  private final String categoryName;

}
