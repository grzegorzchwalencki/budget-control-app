package com.MyApp.budgetControl.domain.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@Entity
@RequiredArgsConstructor
@Table(name = "categories")
@NoArgsConstructor(force = true)
class CategoryEntity {

CategoryEntity(CategoryRequestDTO categoryRequestDTO) {
    this.id = UUID.randomUUID().toString();
    this.categoryName = categoryRequestDTO.getCategoryName();
  }

  @Id
  private final String id;

  @NotBlank(message = "Category name is mandatory")
  @Size(max = 64, message = "Category name max length is 64 char")
  @Column(unique = true)
  private final String categoryName;

}
