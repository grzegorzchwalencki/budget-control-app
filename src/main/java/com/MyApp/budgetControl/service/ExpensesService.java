package com.MyApp.budgetControl.service;

import com.MyApp.budgetControl.model.Expense;
import com.MyApp.budgetControl.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;



@Service
@RequiredArgsConstructor
public class ExpensesService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> getExpenses() {return expenseRepository.getExpensesFromRepository();}

    public Expense getExpenseById(int expenseId) {
        return expenseRepository.getExpensesFromRepository().stream()
                .filter(e -> e.getExpenseId() == expenseId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, (String.format("Expense with Id: %d Not Found", expenseId))));
    }

    public void addNewExpense(Expense newExpense) {
        expenseRepository.addNewExpenseToRepository(newExpense);
    }
}
