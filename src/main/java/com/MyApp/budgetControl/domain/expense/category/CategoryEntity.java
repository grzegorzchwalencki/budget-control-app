package com.MyApp.budgetControl.domain.expense.category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Entity
@RequiredArgsConstructor
@Table(name = "categories")
@NoArgsConstructor(force = true)
public class CategoryEntity {

  public CategoryEntity(CategoryDTO categoryDTO) {
    this.categoryName = categoryDTO.getCategoryName();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private final Long id = null;

  @NotBlank(message = "Category name is mandatory")
  @Size(max = 64, message = "Category name max length is 64 char")
  private final String categoryName;

}
