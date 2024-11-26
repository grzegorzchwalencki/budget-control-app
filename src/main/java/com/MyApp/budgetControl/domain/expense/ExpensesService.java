package com.MyApp.budgetControl.domain.expense;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpensesService {

  private final ExpenseRepository expenseRepository;

  public void saveExpense(ExpenseRequestDTO expenseRequestDTO) {
    ExpenseEntity newExpense = new ExpenseEntity(expenseRequestDTO);
    expenseRepository.save(newExpense);
  }

  public List<ExpenseResponseDTO> findAllExpenses() {
    return expenseRepository.findAll().stream().map(ExpenseResponseDTO::new).toList();
  }

  public ExpenseResponseDTO findExpenseById(String expenseId) {
    return new ExpenseResponseDTO(expenseRepository.findByExpenseId(expenseId).get());
  }

  @Transactional
  public void deleteExpenseById(String expenseId) {
    ExpenseEntity expense = expenseRepository.findByExpenseId(expenseId).get();
    expenseRepository.deleteByExpenseId(expenseId);
  }
}
