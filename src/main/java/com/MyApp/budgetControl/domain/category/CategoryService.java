package com.MyApp.budgetControl.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryEntity saveCategory(CategoryRequestDTO categoryRequestDTO) {
    CategoryEntity newCategory = new CategoryEntity(categoryRequestDTO);
    categoryRepository.save(newCategory);
    return newCategory;
  }

  public List<CategoryResponseDTO> findAllCategories() {
    return  categoryRepository.findAll().stream().map(CategoryResponseDTO::new).toList();
  }

  public CategoryEntity findCategoryById(String categoryId) {
    return categoryRepository.findById(categoryId).get();
  }
}
