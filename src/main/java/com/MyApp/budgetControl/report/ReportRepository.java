package com.MyApp.budgetControl.report;

import com.MyApp.budgetControl.report.dto.CategoryTotalDTO;
import com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface ReportRepository {

  @Query("SELECT new com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO(" +
      "usr.userName , SUM(exp.expenseCost)) " +
      "FROM UserEntity usr " +
      "LEFT JOIN ExpenseEntity exp " +
      "   ON  exp.userId.userId = usr.userId " +
      "   AND exp.expenseDate >= :start " +
      "   AND exp.expenseDate < :end " +
      "WHERE usr.userId = :userId " +
      "GROUP BY usr.userId, usr.userName")
  MonthlyExpenseReportDTO getMonthlyTotalSummaryForUser(
      @Param("userId") String userId,
      @Param("start") Instant start,
      @Param("end") Instant end);

  @Query("SELECT new com.MyApp.budgetControl.report.dto.CategoryTotalDTO(" +
      "cat.categoryName, SUM(exp.expenseCost))" +
      "FROM ExpenseEntity exp " +
      "JOIN CategoryEntity cat ON exp.categoryId.categoryId = cat.categoryId " +
      "WHERE exp.userId.userId = :userId " +
      "AND exp.expenseDate >= :start " +
      "AND exp.expenseDate < :end " +
      "GROUP BY cat.categoryName")
  List<CategoryTotalDTO> getMonthlyCategoriesSummaryForUser(String userId, Instant start, Instant end);

}
