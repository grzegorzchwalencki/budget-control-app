package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import lombok.Value;

@Value
public class CategoryTotalDTO {

  @Id
  private final String categoryName;
  private final Double total;

}

