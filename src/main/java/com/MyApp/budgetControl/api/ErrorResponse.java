package com.MyApp.budgetControl.api;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
class ErrorResponse {

  private final int statusCode;
  private final List<String> errorDetails;
  private final ErrorType errorType;

  public static ErrorResponse forValidationError(List<String> errorDetails) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorDetails, ErrorType.VALIDATION_ERROR);
  }

  public static ErrorResponse forUnhandledError(List<String> errorDetails) {
    return  new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorDetails, ErrorType.UNHANDLED_ERROR);
  }

  public static ErrorResponse forNotFoundError(List<String> errorDetails) {
    return  new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorDetails, ErrorType.NOT_FOUND_ERROR);
  }

  enum ErrorType {
        VALIDATION_ERROR,
        NOT_FOUND_ERROR,
        UNHANDLED_ERROR,
  }
}


