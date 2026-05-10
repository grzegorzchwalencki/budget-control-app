package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import java.math.BigDecimal;

public record CategoryTotalDTO(
    @Id
    String name,
    BigDecimal total
) {
}

