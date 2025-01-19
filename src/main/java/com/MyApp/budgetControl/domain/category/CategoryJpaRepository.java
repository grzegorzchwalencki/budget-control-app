package com.MyApp.budgetControl.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface CategoryJpaRepository extends CategoryRepository, JpaRepository<CategoryEntity, String> {

  CategoryEntity save(CategoryEntity newCategory);
  List<CategoryEntity> findAll();
  Optional<CategoryEntity> findById(String categoryId);
}
