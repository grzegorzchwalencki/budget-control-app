package com.MyApp.budgetControl.report;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import lombok.Value;

@Value
public class MonthBoundariesDates {

  private final Instant firstDayOfMonth;
  private final Instant firstDayOfNextMonth;

  public MonthBoundariesDates(Optional<LocalDate> date) {
    ZoneOffset zone = ZoneOffset.UTC;

    LocalDate firstDayOfMonth = date.map(
            localDate -> localDate.with(TemporalAdjusters.firstDayOfMonth()))
        .orElseGet(() -> LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));

    this.firstDayOfMonth = firstDayOfMonth.atStartOfDay(zone).toInstant();
    this.firstDayOfNextMonth = firstDayOfMonth.plusMonths(1).atStartOfDay(zone).toInstant();
  }


}