package com.MyApp.budgetControl.api;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public @ResponseBody ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<String> errorMessage
        = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
    return ErrorResponse.forValidationError(errorMessage);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = Exception.class)
  public @ResponseBody ErrorResponse handleUnexpectedErrors(Exception ex) {
    List<String> errorMessage = Arrays.asList(
            "Unknown error occured",
            ex.getClass().getCanonicalName(),
            ex.getMessage());
    return ErrorResponse.forUnhandledError(errorMessage);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = NoSuchElementException.class)
  public @ResponseBody ErrorResponse handleNoSuchElementExceptions(NoSuchElementException ex) {
    List<String> errorMessage = List.of("Expense with given Id does not exist");
    return  ErrorResponse.forNotFoundError(errorMessage);
  }
}
