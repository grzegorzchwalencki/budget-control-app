package com.MyApp.budgetControl.report;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.YearMonth;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MonthlyExpenseReportDTO {

  @Id
  private final String userId;
  private final YearMonth yearMonth;
  private final Integer monthlyExpensesTotal;

  @OneToMany(mappedBy = "categoryId")
  private final List<CategoryTotalDTO> categoryId;

}
