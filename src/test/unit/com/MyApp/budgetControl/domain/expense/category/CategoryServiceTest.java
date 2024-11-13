package com.MyApp.budgetControl.domain.expense.category;

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
        new CategoryEntity("groceries"),
        new CategoryEntity("health"));
  }

  @Test
  void getCategoriesShouldReturnAtLeastOneExpanse() {
    when(mockRepository.findAll()).thenReturn(categories);
    List<CategoryEntity> categories = subject.findAllCategories();
    CategoryEntity expected = new CategoryEntity(
        "groceries"
    );
    assertTrue(categories.contains(expected));
  }
}
