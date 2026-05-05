package com.MyApp.budgetControl.report


import com.MyApp.budgetControl.report.dto.CategoryTotalDTO
import com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters

class ReportServiceTest extends Specification {

    def repository = Mock(ReportRepository)
    def service = new ReportService(repository)

    def "should return MonthlyReportDTO with expected values depends on input date"() {
        given:
            def reportDTO = new MonthlyExpenseReportDTO("userName", BigDecimal.valueOf(200))
            def categoryListDTO = List.of(new CategoryTotalDTO("categoryName", BigDecimal.valueOf(200)))
            def firstDayOfExpectedMonth = LocalDateTime.of(expectedDate, LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).atZone(ZoneOffset.UTC).toInstant()

            1 * repository.getMonthlyTotalSummaryForUser(_, _, _) >> reportDTO
            1 * repository.getMonthlyCategoriesSummaryForUser(_, _, _) >> categoryListDTO
        when:
            def result = service.getMonthlyReport("userId", inputDate as LocalDate)

        then:

            result.userName == "userName"
            result.firstDayOfMonth == firstDayOfExpectedMonth
            result.monthlyExpensesTotal == BigDecimal.valueOf(200)
            result.categories == List.of(new CategoryTotalDTO("categoryName", BigDecimal.valueOf(200)))

        where:
            inputDate                  | expectedDate
            LocalDate.now()            | LocalDate.now()
            null                       | LocalDate.now()
            LocalDate.of(2025, 5, 5)   | LocalDate.of(2025, 5, 1)
            LocalDate.of(2025, 1, 1)   | LocalDate.of(2025, 1, 1)
            LocalDate.of(2025, 12, 31) | LocalDate.of(2025, 12, 1)
    }


}
