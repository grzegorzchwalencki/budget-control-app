package com.MyApp.budgetControl.domain.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
    @NotBlank(message = "Category name is mandatory")
    @Size(max = 64, message = "Category name max length is 64 char")
    @JsonProperty("categoryName")
    String categoryName) {
}
