package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import java.math.BigDecimal;

public record MonthlyExpenseReportDTO(
    @Id
    String userName,
    BigDecimal monthlyExpensesTotal) {
}

