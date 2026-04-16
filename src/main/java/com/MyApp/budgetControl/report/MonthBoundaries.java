package com.MyApp.budgetControl.report;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
class MonthBoundaries {

  private final Instant firstDayOfMonth;
  private final Instant firstDayOfNextMonth;

  public MonthBoundaries(LocalDate date) {
    ZoneOffset zone = ZoneOffset.UTC;
    LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());

    this.firstDayOfMonth = firstDayOfMonth.atStartOfDay(zone).toInstant();
    this.firstDayOfNextMonth = firstDayOfMonth.plusMonths(1).atStartOfDay(zone).toInstant();
  }

}