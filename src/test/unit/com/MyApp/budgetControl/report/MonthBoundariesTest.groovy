package com.MyApp.budgetControl.report

import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class MonthBoundariesTest extends Specification {

    def "should find first day of given and next month from input date and map it to Instant"() {
        given:
            def inputDate = date
            def expectedFirstDayInstant = expectedFirstDayOfMonth.atZone(ZoneOffset.UTC).toInstant()
            def expectedFirstDayOfNextMonthInstant = expectedFirstDayOfNextMonth.atZone(ZoneOffset.UTC).toInstant()

        when:
            MonthBoundaries boundariesDates = new MonthBoundaries(inputDate)

        then:
            boundariesDates.firstDayOfMonth == expectedFirstDayInstant
            boundariesDates.firstDayOfNextMonth == expectedFirstDayOfNextMonthInstant

        where:
            date                       | expectedFirstDayOfMonth                | expectedFirstDayOfNextMonth
            LocalDate.of(2026, 3, 11)  | LocalDateTime.of(2026, 3, 1, 0, 0, 0)  | LocalDateTime.of(2026, 4, 1, 0, 0, 0)
            LocalDate.of(2024, 2, 29)  | LocalDateTime.of(2024, 2, 1, 0, 0, 0)  | LocalDateTime.of(2024, 3, 1, 0, 0, 0)
            LocalDate.of(2025, 12, 11) | LocalDateTime.of(2025, 12, 1, 0, 0, 0) | LocalDateTime.of(2026, 1, 1, 0, 0, 0)
    }

}
