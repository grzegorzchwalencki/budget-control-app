package com.MyApp.budgetControl.report;

import com.MyApp.budgetControl.domain.expense.ExpenseEntity;
import com.MyApp.budgetControl.report.dto.CategoryTotalDTO;
import com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface ReportJpaRepository extends ReportRepository, JpaRepository<ExpenseEntity, UUID> {

  MonthlyExpenseReportDTO getMonthlyTotalSummaryForUser(UUID userId, Instant start, Instant end);

  List<CategoryTotalDTO> getMonthlyCategoriesSummaryForUser(UUID userId, Instant start, Instant end);
}
