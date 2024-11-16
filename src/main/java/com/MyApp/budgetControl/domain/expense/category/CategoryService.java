package com.MyApp.budgetControl.domain.expense.category;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public Category saveCategory(Category category) {
    return categoryRepository.save(category);
  }

  public List<Category> findAllCategories() {
    return  categoryRepository.findAll();
  }
}
