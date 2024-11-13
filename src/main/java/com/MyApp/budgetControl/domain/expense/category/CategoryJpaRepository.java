package com.MyApp.budgetControl.domain.expense.category;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends CategoryRepository, JpaRepository<CategoryEntity, Long> {

  CategoryEntity save(CategoryEntity newCategory);
  List<CategoryEntity> findAll();

}
