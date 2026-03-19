package com.MyApp.budgetControl.report;

import com.MyApp.budgetControl.report.dto.CategoryTotalDTO;
import com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO;
import com.MyApp.budgetControl.report.dto.MonthlyReportDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

  private final ReportRepository repository;

  public MonthlyReportDTO getMonthlyReport(String userId, Optional<LocalDate> date) {

    MonthBoundariesDates monthBoundaries = new MonthBoundariesDates(date);

    MonthlyExpenseReportDTO summary =
        repository.getMonthlyTotalSummaryForUser(userId,
            monthBoundaries.getFirstDayOfMonth(),
            monthBoundaries.getFirstDayOfNextMonth());

    List<CategoryTotalDTO> categories =
        repository.getMonthlyCategoriesSummaryforUser(userId,
            monthBoundaries.getFirstDayOfMonth(),
            monthBoundaries.getFirstDayOfNextMonth());

    return new MonthlyReportDTO(
        summary.getUserName(),
        monthBoundaries.getFirstDayOfMonth(),
        summary.getMonthlyExpensesTotal(),
        categories
    );

  }
}
