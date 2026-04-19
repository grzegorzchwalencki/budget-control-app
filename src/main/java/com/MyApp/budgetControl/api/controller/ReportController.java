package com.MyApp.budgetControl.api.controller;

import com.MyApp.budgetControl.report.ReportService;
import com.MyApp.budgetControl.report.dto.MonthlyReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Report")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

  private final ReportService reportService;

  @Operation(summary = "Get a monthly expense report showing the total amount and the amounts spent in each category")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Report generated")})
  @GetMapping("/users/{userId}/expenses/monthly-report")
  public MonthlyReportDTO getMonthlyReport(@PathVariable String userId, @RequestParam(required = false) LocalDate date) {
    return reportService.getMonthlyReport(userId, date);
  }

}
