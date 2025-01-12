package com.MyApp.budgetControl.domain.expense;

import com.MyApp.budgetControl.domain.category.CategoryEntity;
import com.MyApp.budgetControl.domain.user.UserEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpensesService {

  private final ExpenseRepository expenseRepository;

  public ExpenseEntity saveExpense(ExpenseRequestDTO expenseRequestDTO, CategoryEntity category, UserEntity user) {
    ExpenseEntity newExpense = new ExpenseEntity(expenseRequestDTO, category, user);
    return expenseRepository.save(newExpense);
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
