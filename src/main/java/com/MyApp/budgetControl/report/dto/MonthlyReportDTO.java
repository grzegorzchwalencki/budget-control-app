package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import java.time.Instant;
import java.util.List;
import lombok.Value;

@Value
public class MonthlyReportDTO {

  @Id
  private final String userName;
  private final Instant firstDayOfMonth;
  private final Double monthlyExpensesTotal;
  private final List<CategoryTotalDTO> category;


}
