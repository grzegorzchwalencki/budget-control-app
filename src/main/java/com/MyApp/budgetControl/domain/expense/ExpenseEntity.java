package com.MyApp.budgetControl.domain.expense;

import com.MyApp.budgetControl.domain.category.CategoryEntity;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO;
import com.MyApp.budgetControl.domain.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Value
@Entity
@RequiredArgsConstructor
@Table(name = "expenses")
@NoArgsConstructor(force = true)
@EntityListeners(AuditingEntityListener.class)
public class ExpenseEntity {

  ExpenseEntity(ExpenseRequestDTO expenseRequestDTO, CategoryEntity categoryId, UserEntity userId) {
    this.expenseId = UUID.randomUUID().toString();
    this.expenseCost = expenseRequestDTO.getExpenseCost();
    this.categoryId = categoryId;
    this.expenseComment = expenseRequestDTO.getExpenseComment();
    this.expenseDate = Instant.now();
    this.userId = userId;
  }

  @Id
  private final String expenseId;

  @NotNull(message = "Cost value is mandatory")
  @DecimalMin(value = "0.01", message = "Cost value should be positive")
  private final double expenseCost;

  @NotNull(message = "Category is mandatory")
  @ManyToOne
  private final CategoryEntity categoryId;

  @NotBlank(message = "Comment is mandatory")
  @Size(max = 128, message = "Comment max length is 128 char")
  private final String expenseComment;

  @NotNull
  private final Instant expenseDate;

  @NotNull(message = "User id is mandatory")
  @ManyToOne
  private final UserEntity userId;

}
