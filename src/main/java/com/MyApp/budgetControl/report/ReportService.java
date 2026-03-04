package com.MyApp.budgetControl.report;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final ReportRepository repository;

  public MonthlyExpenseReportDTO getMonthlyExpenseReport(String userid, Optional<YearMonth> yearMonth) {
    Instant start = yearMonth.get().atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant();
    Instant end = yearMonth.get().plusMonths(1).atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant();
    repository.getMonthlyTotalSummaryForUser(userid, start, end);
    repository.getMonthlyCategoriesSummaryforUser(userid, start, end);
    return new MonthlyExpenseReportDTO();
  }


}
