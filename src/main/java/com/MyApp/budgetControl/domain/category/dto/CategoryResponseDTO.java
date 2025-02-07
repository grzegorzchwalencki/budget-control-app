package com.MyApp.budgetControl.domain.category.dto;

import com.MyApp.budgetControl.domain.category.CategoryEntity;
import lombok.Value;

@Value
public class CategoryResponseDTO {

  public CategoryResponseDTO(CategoryEntity categoryEntity) {
    this.categoryId = categoryEntity.getCategoryId();
    this.categoryName = categoryEntity.getCategoryName();
  }

  private final String categoryId;
  private final String categoryName;

}
