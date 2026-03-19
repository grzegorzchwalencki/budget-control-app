package com.MyApp.budgetControl.report;

import com.MyApp.budgetControl.domain.expense.ExpenseEntity;
import com.MyApp.budgetControl.report.dto.CategoryTotalDTO;
import com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface ReportJpaRepository extends ReportRepository, JpaRepository<ExpenseEntity, String> {

  MonthlyExpenseReportDTO getMonthlyTotalSummaryForUser(String userId, Instant start, Instant end);

  List<CategoryTotalDTO> getMonthlyCategoriesSummaryforUser(String userId, Instant start, Instant end);
}
