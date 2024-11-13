package com.MyApp.budgetControl.domain.expense.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public void saveCategory(CategoryEntity newCategory) {
    categoryRepository.save(newCategory);
  }

  public List<CategoryEntity> findAllCategories() {
    return  categoryRepository.findAll();
  }
}
