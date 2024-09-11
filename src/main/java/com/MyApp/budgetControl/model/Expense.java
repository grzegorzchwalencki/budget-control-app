package com.MyApp.budgetControl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
@Data
@AllArgsConstructor
@Component
@RequiredArgsConstructor
public class Expense {

    private int expenseId;
    private double expenseCost;
    private String expenseCategory;
    private String expenseComment;
    private String expenseDate;

}
