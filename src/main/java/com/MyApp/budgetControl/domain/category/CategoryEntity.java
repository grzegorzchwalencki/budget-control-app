package com.MyApp.budgetControl.domain.category;

import com.MyApp.budgetControl.domain.category.dto.CategoryRequestDTO;
import com.MyApp.budgetControl.domain.expense.ExpenseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Value
@Entity
@RequiredArgsConstructor
@Table(name = "categories")
@NoArgsConstructor(force = true)
public class CategoryEntity {

  CategoryEntity(CategoryRequestDTO categoryRequestDTO) {
    this.categoryId = UUID.randomUUID().toString();
    this.categoryName = categoryRequestDTO.getCategoryName();
    this.categoryExpenses = Collections.emptyList();
  }

  @Id
  private final String categoryId;

  @NotBlank(message = "Category name is mandatory")
  @Size(max = 64, message = "Category name max length is 64 char")
  @Column(unique = true)
  private final String categoryName;

  @OneToMany(mappedBy = "categoryId")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<ExpenseEntity> categoryExpenses;


}
