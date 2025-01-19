package com.MyApp.budgetControl.domain.category;

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
