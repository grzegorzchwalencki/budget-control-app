package com.MyApp.budgetControl.api.utils

import com.MyApp.budgetControl.api.FunctionalTestConfiguration
import io.restassured.response.Response

class ErrorValidator extends FunctionalTestConfiguration {

    def assertErrorMessage(Response response, ExpectedErrors expectedError, String expectedDetails = "") {
        def expected = switch (expectedError) {
            case ExpectedErrors.NOT_FOUND -> [statusCode: 404, errorDetails: "[Element with given Id does not exist]", errorType: "NOT_FOUND_ERROR"]
            case ExpectedErrors.CONFLICT_ERROR -> [statusCode: 409, errorDetails: "[Name is already used. Please choose a different one]", errorType: "CONFLICT_ERROR"]
            case ExpectedErrors.VALIDATION_ERROR -> [statusCode: 400, errorDetails: expectedDetails, errorType: "VALIDATION_ERROR"]
            default -> throw new IllegalArgumentException("Unknown error type: $expectedError")
        }

        assert response.statusCode() == expected.statusCode

        with(response.jsonPath()) {
            assert getString("errorDetails").contains(expected.errorDetails)
            assert getString("errorType") == expected.errorType
        }

        return true
    }

    enum ExpectedErrors {
        NOT_FOUND,
        CONFLICT_ERROR,
        VALIDATION_ERROR
    }

}
