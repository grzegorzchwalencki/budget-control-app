package com.MyApp.budgetControl.domain.category;

import lombok.Value;

@Value
public class CategoryResponseDTO {

  public CategoryResponseDTO(CategoryEntity categoryEntity) {
    this.id = categoryEntity.getId();
    this.categoryName = categoryEntity.getCategoryName();
  }

  private final String id;
  private final String categoryName;

}
