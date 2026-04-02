package com.MyApp.budgetControl.report;

import com.MyApp.budgetControl.report.dto.CategoryTotalDTO;
import com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

interface ReportRepository {

  @Query("SELECT new com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO(" +
      "usr.userName , SUM(exp.expenseCost)) " +
      "FROM UserEntity usr " +
      "LEFT JOIN ExpenseEntity exp " +
      "   ON  exp.userId.userId = usr.userId " +
      "   AND exp.expenseDate >= ?2 " +
      "   AND exp.expenseDate < ?3 " +
      "WHERE usr.userId = ?1 " +
      "GROUP BY usr.userId, usr.userName")
  MonthlyExpenseReportDTO getMonthlyTotalSummaryForUser(String userId, Instant start, Instant end);

  @Query("SELECT new com.MyApp.budgetControl.report.dto.CategoryTotalDTO(" +
      "cat.categoryName, SUM(exp.expenseCost))" +
      "FROM ExpenseEntity exp " +
      "JOIN CategoryEntity cat ON exp.categoryId.categoryId = cat.categoryId " +
      "WHERE exp.userId.userId = ?1 " +
      "AND exp.expenseDate >= ?2 " +
      "AND exp.expenseDate < ?3 " +
      "GROUP BY cat.categoryName")
  List<CategoryTotalDTO> getMonthlyCategoriesSummaryForUser(String userId, Instant start, Instant end);

}
