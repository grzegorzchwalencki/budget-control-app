package com.MyApp.budgetControl.api;

import com.MyApp.budgetControl.domain.category.CategoryRequestDTO;
import com.MyApp.budgetControl.domain.category.CategoryResponseDTO;
import com.MyApp.budgetControl.domain.category.CategoryService;
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
  public void addNewCategory(@Valid @RequestBody CategoryRequestDTO newCategory) {
    categoryService.saveCategory(newCategory);
  }

  @GetMapping
  public List<CategoryResponseDTO> getCategories() {
    return categoryService.findAllCategories();
  }
}
