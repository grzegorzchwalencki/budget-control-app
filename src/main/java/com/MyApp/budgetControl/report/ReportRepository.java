package com.MyApp.budgetControl.report;

import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository {

  @Query("SELECT new MonthlyExpenseReportDTO(exp.userId, :start, SUM(exp.expenseCost)) " +
      "FROM ExpenseEntity exp " +
      "WHERE exp.userId=:userId AND exp.expenseDate >= :start AND exp.expenseDate < :end")
  MonthlyExpenseReportDTO getMonthlyTotalSummaryForUser(String userId, Instant start, Instant end);


  @Query("SELECT new CategoryTotalDTO(exp.categoryId.categoryId, cat.categoryName, sum(exp.expenseCost)) " +
      "FROM ExpenseEntity exp " +
      "JOIN CategoryEntity cat ON exp.categoryId = cat.categoryId WHERE exp.userId=:userId AND exp.expenseDate >= :start AND exp.expenseDate < :end GROUP BY exp.categoryId")
  List<CategoryTotalDTO> getMonthlyCategoriesSummaryforUser(String userId, Instant start, Instant end);
}
