package com.MyApp.budgetControl.api;

import com.MyApp.budgetControl.domain.expense.category.CategoryDTO;
import com.MyApp.budgetControl.domain.expense.category.CategoryEntity;
import com.MyApp.budgetControl.domain.expense.category.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryEntity addNewCategory(@Valid @RequestBody CategoryDTO newCategory) {
    CategoryEntity category = new CategoryEntity(newCategory);
    categoryService.saveCategory(category);
    return category;
  }

  @GetMapping
  public List<CategoryEntity> getCategories() {
    return categoryService.findAllCategories();
  }
}
