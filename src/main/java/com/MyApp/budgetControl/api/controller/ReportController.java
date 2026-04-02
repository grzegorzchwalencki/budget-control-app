package com.MyApp.budgetControl.api.controller;

import com.MyApp.budgetControl.report.ReportService;
import com.MyApp.budgetControl.report.dto.MonthlyReportDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Report")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

  private final ReportService reportService;

  @GetMapping("/users/{userId}/expenses/monthly-report")
  public MonthlyReportDTO getMonthlyReport(@PathVariable String userId, Optional<LocalDate> date) {
    return reportService.getMonthlyReport(userId, date);
  }

}
