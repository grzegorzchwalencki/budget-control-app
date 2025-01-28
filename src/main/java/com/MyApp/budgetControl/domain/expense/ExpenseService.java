package com.MyApp.budgetControl.domain.expense;

import com.MyApp.budgetControl.domain.category.CategoryEntity;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseResponseDTO;
import com.MyApp.budgetControl.domain.user.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    ExpenseEntity expense = expenseRepository.findById(expenseId).get();
    expenseRepository.deleteById(expenseId);
  }
}
