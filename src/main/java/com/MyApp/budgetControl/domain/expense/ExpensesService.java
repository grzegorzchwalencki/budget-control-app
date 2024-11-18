package com.MyApp.budgetControl.domain.expense;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpensesService {

  private final ExpenseRepository expenseRepository;

  public void saveExpense(ExpenseEntity newExpense) {
    expenseRepository.save(newExpense);
  }

  public List<ExpenseEntity> findAllExpenses() {
    return expenseRepository.findAll();
  }

  public ExpenseEntity findExpenseById(UUID expenseId) {
    return expenseRepository.findByExpenseId(expenseId).get();
  }

  @Transactional
  public void deleteExpenseById(UUID expenseId) {
    expenseRepository.deleteByExpenseId(expenseId);
  }
}
