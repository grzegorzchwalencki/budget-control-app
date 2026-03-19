package com.MyApp.budgetControl.report;

import com.MyApp.budgetControl.report.dto.CategoryTotalDTO;
import com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

interface ReportRepository {

  @Query("SELECT new com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO(" +
      "exp.userId.userName, SUM(exp.expenseCost)) " +
      "FROM ExpenseEntity exp " +
      "WHERE exp.userId.userId = ?1 " +
      "AND exp.expenseDate >= ?2 " +
      "AND exp.expenseDate < ?3 " +
      "GROUP BY exp.userId")
  MonthlyExpenseReportDTO getMonthlyTotalSummaryForUser(String userId, Instant start, Instant end);


  @Query("SELECT new com.MyApp.budgetControl.report.dto.CategoryTotalDTO(" +
      "cat.categoryName, SUM(exp.expenseCost))" +
      "FROM ExpenseEntity exp " +
      "JOIN CategoryEntity cat ON exp.categoryId.categoryId = cat.categoryId " +
      "WHERE exp.userId.userId = ?1 " +
      "AND exp.expenseDate >= ?2 " +
      "AND exp.expenseDate < ?3 " +
      "GROUP BY cat.categoryName")
  List<CategoryTotalDTO> getMonthlyCategoriesSummaryforUser(String userId, Instant start, Instant end);

}
