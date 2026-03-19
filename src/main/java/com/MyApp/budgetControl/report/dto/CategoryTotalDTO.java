package com.MyApp.budgetControl.report.dto;

import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class CategoryTotalDTO {

  @Id
  private final String categoryName;
  private final Double total;

}

