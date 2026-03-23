package com.MyApp.budgetControl.report;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class BoundariesService {

  public MonthBoundaries getMonthBoundaries(LocalDate date) {
    return new MonthBoundaries(date);
  }
}
