package com.MyApp.budgetControl.api.controller;

import com.MyApp.budgetControl.report.MonthlyExpenseReportDTO;
import com.MyApp.budgetControl.report.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.YearMonth;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Report")
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

  private final ReportService reportService;

  @GetMapping("/users/{userid}/expenses/monthly-report")
  public MonthlyExpenseReportDTO getMonthlyExpenseReport(@PathVariable String userid, Optional<YearMonth> yearMonth) {
    return reportService.getMonthlyExpenseReport(userid, yearMonth);
  }

}
