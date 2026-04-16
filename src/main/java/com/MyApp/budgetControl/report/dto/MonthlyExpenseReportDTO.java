package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Value;

@Value
public class MonthlyExpenseReportDTO {

  @Id
  private final String userName;
  private final BigDecimal monthlyExpensesTotal;
}

