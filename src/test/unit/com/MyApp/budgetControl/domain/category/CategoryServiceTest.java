package com.MyApp.budgetControl.domain.category;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @Mock
  CategoryRepository mockRepository;
  @InjectMocks
  CategoryService subject;

  static List<CategoryEntity> categories;

  @BeforeAll
  static void setup() {
    categories = Arrays.asList(
        new CategoryEntity( new CategoryRequestDTO("groceries")),
        new CategoryEntity(new CategoryRequestDTO("health")));
  }

  @Test
  void getCategoriesShouldReturnAtLeastOneCategory() {
    when(mockRepository.findAll()).thenReturn(categories);
    List<CategoryResponseDTO> categories = subject.findAllCategories();
    String expectedCategoryName = "health";
    assertTrue(categories.stream().anyMatch(x -> x.getCategoryName().equals(expectedCategoryName)));
  }
}
