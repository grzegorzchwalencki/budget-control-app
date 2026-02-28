package com.MyApp.budgetControl.domain.expense;

import com.MyApp.budgetControl.domain.category.CategoryEntity;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseResponseDTO;
import com.MyApp.budgetControl.domain.user.UserEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {

  private final ExpenseRepository expenseRepository;

  @Transactional
  public ExpenseEntity saveExpense(ExpenseRequestDTO expenseRequestDTO, CategoryEntity category, UserEntity user) {
    ExpenseEntity newExpense = new ExpenseEntity(expenseRequestDTO, category, user);
    expenseRepository.save(newExpense);
    user.getUserExpenses().add(newExpense);
    category.getCategoryExpenses().add(newExpense);
    return newExpense;
  }

  public List<ExpenseResponseDTO> findAllExpenses() {
    return expenseRepository.findAll().stream().map(ExpenseResponseDTO::new).toList();
  }

  public ExpenseResponseDTO findExpenseById(String expenseId) {
    return new ExpenseResponseDTO(expenseRepository.findById(expenseId).get());
  }

  @Transactional
  public void deleteExpenseById(String expenseId) {
    expenseRepository.findById(expenseId)
        .orElseThrow(() -> new NoSuchElementException("Expense with id " + expenseId + " not found"));
    expenseRepository.deleteById(expenseId);
  }
}
