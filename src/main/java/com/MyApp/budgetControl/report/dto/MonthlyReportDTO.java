package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record MonthlyReportDTO(

    @Id
    String userName,
    Instant firstDayOfMonth,
    BigDecimal monthlyExpensesTotal,
    List<CategoryTotalDTO> categories) {
}
