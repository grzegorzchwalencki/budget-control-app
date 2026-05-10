package com.MyApp.budgetControl.domain.category.dto;

import com.MyApp.budgetControl.domain.category.CategoryEntity;
import java.util.UUID;

public record CategoryResponseDTO(
    UUID categoryId,
    String categoryName
) {

  public CategoryResponseDTO(CategoryEntity entity) {
    this(
        entity.getCategoryId(),
        entity.getCategoryName()
    );
  }

}
