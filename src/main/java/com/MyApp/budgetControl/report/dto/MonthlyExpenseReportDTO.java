package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import lombok.Value;

@Value
public class MonthlyExpenseReportDTO {

  @Id
  private final String userName;
  private final double monthlyExpensesTotal;
}

