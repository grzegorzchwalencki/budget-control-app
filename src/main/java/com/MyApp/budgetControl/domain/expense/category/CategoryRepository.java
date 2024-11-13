package com.MyApp.budgetControl.domain.expense.category;

import java.util.List;

public interface CategoryRepository {

  CategoryEntity save(CategoryEntity newCategory);

  List<CategoryEntity> findAll();

}
