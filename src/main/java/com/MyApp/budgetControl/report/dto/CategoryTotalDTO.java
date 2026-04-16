package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Value;

@Value
public class CategoryTotalDTO {

  @Id
  private final String name;
  private final BigDecimal total;

}

