package com.MyApp.budgetControl.domain.expense;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class ExpenseEntity {

    public ExpenseEntity(ExpenseDTO expenseDTO) {
        this.expenseId = UUID.randomUUID();
        this.expenseCost = expenseDTO.getExpenseCost();
        this.expenseCategory = expenseDTO.getExpenseCategory();
        this.expenseComment = expenseDTO.getExpenseComment();
        this.expenseDate = Instant.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final UUID expenseId;

    @NotNull(message = "Cost value is mandatory")
    @DecimalMin(value = "0.01", message = "Cost value should be positive")
    private final double expenseCost;

    @NotBlank(message = "Category is mandatory")
    @Size(max=64, message = "Category max length is 64 char")
    private final String expenseCategory;

    @NotBlank(message = "Comment is mandatory")
    @Size(max = 128, message = "Comment max length is 128 char")
    private final String expenseComment;

    @NotNull
    private final Instant expenseDate;

}
