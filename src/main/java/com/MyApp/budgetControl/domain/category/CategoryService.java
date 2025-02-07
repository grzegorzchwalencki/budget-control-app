package com.MyApp.budgetControl.domain.category;

import com.MyApp.budgetControl.domain.category.dto.CategoryRequestDTO;
import com.MyApp.budgetControl.domain.category.dto.CategoryResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
