package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MonthlyExpenseReportDTO {

  @Id
  private final String userName;
  private final double monthlyExpensesTotal;
}

