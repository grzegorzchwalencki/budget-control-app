package com.MyApp.budgetControl.api;

import com.MyApp.budgetControl.domain.expense.category.Category;
import com.MyApp.budgetControl.domain.expense.category.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public Category createCategory(@RequestBody Category category) {
    return categoryService.saveCategory(category);
  }

  @GetMapping
  public List<Category> getCategories() {
    return categoryService.findAllCategories();
  }
}
