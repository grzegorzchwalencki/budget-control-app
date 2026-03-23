package com.MyApp.budgetControl.report

import com.MyApp.budgetControl.report.dto.CategoryTotalDTO
import com.MyApp.budgetControl.report.dto.MonthlyExpenseReportDTO
import spock.lang.Specification

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters

class ReportServiceTest extends Specification {

    def repository = Mock(ReportRepository)
    def boundariesService = Mock(BoundariesService)
    def service = new ReportService(repository, boundariesService)

    def "should return MonthlyReportDTO with expected values"() {
        given:
            def testBoundaries = new MonthBoundaries(Instant.parse("2025-03-01T00:00:00Z"), Instant.parse("2025-04-01T00:00:00Z"))
            def reportDTO = new MonthlyExpenseReportDTO("userName", 200)
            def categoryListDTO = List.of(new CategoryTotalDTO("categoryName", 200))
            def expectedDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).atZone(ZoneOffset.UTC).toInstant()

            1 * boundariesService.getMonthBoundaries(_) >> testBoundaries
            1 * repository.getMonthlyTotalSummaryForUser(_, _, _) >> reportDTO
            1 * repository.getMonthlyCategoriesSummaryforUser(_, _, _) >> categoryListDTO
        when:
            def result = service.getMonthlyReport("userId", Optional.of(LocalDate.now()))

        then:
            result.with() {
                userName == "userName"
                firstDayOfMonth == expectedDate
                monthlyExpensesTotal == 200
                category == List.of(new CategoryTotalDTO("categoryName", 200))
            }
    }

}
