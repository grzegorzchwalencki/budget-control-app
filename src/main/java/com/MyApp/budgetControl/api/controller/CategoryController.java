package com.MyApp.budgetControl.api.controller;

import com.MyApp.budgetControl.domain.ServicesOrchestrator;
import com.MyApp.budgetControl.domain.category.dto.CategoryRequestDTO;
import com.MyApp.budgetControl.domain.category.dto.CategoryResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

  private final ServicesOrchestrator servicesOrchestrator;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addNewCategory(@Valid @RequestBody CategoryRequestDTO newCategory) {
    servicesOrchestrator.saveCategory(newCategory);
  }

  @GetMapping
  public List<CategoryResponseDTO> getCategories() {
    return servicesOrchestrator.findAllCategories();
  }
}
