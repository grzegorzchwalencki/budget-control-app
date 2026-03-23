package com.MyApp.budgetControl.report

import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class MonthBoundariesTest extends Specification {

    def "should find first day of given and next month from input date and map it to Instant"() {
        given:
            def inputDate = LocalDate.of(2026, 03, 11)
            def expectedFirstDayOfMonth = LocalDateTime.of(2026, 03, 1, 0, 0, 0)
            def expectedFirstDayInstant = expectedFirstDayOfMonth.atZone(ZoneOffset.UTC).toInstant()
            def expectedFirstDayOfNextMonthInstant = expectedFirstDayOfMonth.plusMonths(1).atZone(ZoneOffset.UTC).toInstant()

        when:
            MonthBoundaries boundariesDates = new MonthBoundaries(inputDate)

        then:
            boundariesDates.firstDayOfMonth == expectedFirstDayInstant
            boundariesDates.firstDayOfNextMonth == expectedFirstDayOfNextMonthInstant
    }

}
