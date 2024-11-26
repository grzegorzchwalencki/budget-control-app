package com.MyApp.budgetControl.domain.category;

import java.util.List;

interface CategoryRepository {

  CategoryEntity save(CategoryEntity newCategory);

  List<CategoryEntity> findAll();

}
