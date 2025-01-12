package com.MyApp.budgetControl.domain.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public void saveCategory(CategoryRequestDTO categoryRequestDTO) {
    CategoryEntity newCategory = new CategoryEntity(categoryRequestDTO);
    categoryRepository.save(newCategory);
  }

  public List<CategoryResponseDTO> findAllCategories() {
    return  categoryRepository.findAll().stream().map(CategoryResponseDTO::new).toList();
  }

  public CategoryEntity findCategoryById(String categoryId) {
    return categoryRepository.findById(categoryId).get();
  }
}
