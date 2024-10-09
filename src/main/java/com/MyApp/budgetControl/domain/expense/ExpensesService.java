package com.MyApp.budgetControl.domain.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpensesService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void saveExpense(ExpenseEntity newExpense){
        expenseRepository.save(newExpense);
    }

    public List<ExpenseEntity> findAllExpenses() {
        return (List<ExpenseEntity>) expenseRepository.findAll();
    }

    public ExpenseEntity findExpenseById(UUID expenseId) {
        return expenseRepository.findById(expenseId).get();
    }
}
