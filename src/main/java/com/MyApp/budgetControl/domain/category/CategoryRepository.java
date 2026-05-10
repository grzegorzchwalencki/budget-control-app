package com.MyApp.budgetControl.domain.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface CategoryRepository {

  CategoryEntity save(CategoryEntity newCategory);

  List<CategoryEntity> findAll();

  Optional<CategoryEntity> findById(UUID categoryId);

}
