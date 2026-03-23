package com.MyApp.budgetControl.report

import spock.lang.Specification

import java.time.LocalDate

class BoundariesServiceTest extends Specification {

    def service = new BoundariesService()

    def "test"() {
        given:
            def date = LocalDate.now()

        when:
            def result = service.getMonthBoundaries(date)

        then:
            result == new MonthBoundaries(date)
    }


}
