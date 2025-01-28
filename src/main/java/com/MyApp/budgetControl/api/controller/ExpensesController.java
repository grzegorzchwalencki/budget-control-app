package com.MyApp.budgetControl.api.controller;

import com.MyApp.budgetControl.domain.ServicesOrchestrator;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpensesController {

  private final ServicesOrchestrator servicesOrchestrator;

  @GetMapping
  public List<ExpenseResponseDTO> getExpenses() {
    return servicesOrchestrator.findAllExpenses();
  }

  @GetMapping("/{expenseId}")
  public ExpenseResponseDTO getExpenseById(@PathVariable String expenseId) {
    return servicesOrchestrator.findExpenseById(expenseId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addNewExpense(@Valid @RequestBody ExpenseRequestDTO newExpense) {
    servicesOrchestrator.saveExpense(newExpense);
  }

  @DeleteMapping("/{expenseId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteExpense(@PathVariable String expenseId) {
    servicesOrchestrator.deleteExpenseById(expenseId);
  }
}