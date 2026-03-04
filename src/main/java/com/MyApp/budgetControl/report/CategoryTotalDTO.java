package com.MyApp.budgetControl.report;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CategoryTotalDTO {
  @Id
  private final String categoryId;
  private final String categoryName;
  private final Double total;

}

