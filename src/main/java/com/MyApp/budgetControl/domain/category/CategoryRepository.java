package com.MyApp.budgetControl.domain.category;

import java.util.List;
import java.util.Optional;

interface CategoryRepository {

  CategoryEntity save(CategoryEntity newCategory);

  List<CategoryEntity> findAll();

  Optional<CategoryEntity> findById(String categoryId);

}
