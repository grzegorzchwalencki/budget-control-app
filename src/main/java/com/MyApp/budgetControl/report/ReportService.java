package com.MyApp.budgetControl.report;

import com.MyApp.budgetControl.report.dto.CategoryTotalDTO;
import com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO;
import com.MyApp.budgetControl.report.dto.MonthlyReportDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

  private final ReportRepository repository;

  public MonthlyReportDTO getMonthlyReport(UUID userId, LocalDate date) {

    LocalDate effectiveDate = Optional.ofNullable(date).orElse(LocalDate.now());
    MonthBoundaries monthBoundaries = new MonthBoundaries(effectiveDate);

    MonthlyExpenseReportDTO summary =
        repository.getMonthlyTotalSummaryForUser(userId,
            monthBoundaries.getFirstDayOfMonth(),
            monthBoundaries.getFirstDayOfNextMonth());

    List<CategoryTotalDTO> categories =
        repository.getMonthlyCategoriesSummaryForUser(userId,
            monthBoundaries.getFirstDayOfMonth(),
            monthBoundaries.getFirstDayOfNextMonth());

    return new MonthlyReportDTO(
        summary.userName(),
        monthBoundaries.getFirstDayOfMonth(),
        summary.monthlyExpensesTotal(),
        categories
    );

  }

}
