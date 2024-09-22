package com.MyApp.budgetControl.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final int statusCode;
    private final List<String> errorDetails;
    private final ErrorType errorType;

    public static ErrorResponse forValidationError(List<String> errorDetails) {
        return  new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorDetails, ErrorType.VALIDATION_ERROR);
    }

    enum ErrorType {
        VALIDATION_ERROR,
        UNHANDLED_ERROR
    }

}


