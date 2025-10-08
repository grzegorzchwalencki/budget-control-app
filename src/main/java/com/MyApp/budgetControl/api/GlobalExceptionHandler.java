package com.MyApp.budgetControl.api;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalExceptionHandler {

  public static String notFoundMessage = "Element with given Id does not exist";
  public static String conflictMessage = "Name is already used. Please choose a different one";
  public static String unknownErrorMessage = "Unknown error occured";

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
        unknownErrorMessage,
        ex.getClass().getCanonicalName(),
        ex.getMessage());
    return ErrorResponse.forUnhandledError(errorMessage);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = NoSuchElementException.class)
  public @ResponseBody ErrorResponse handleNoSuchElementExceptions(NoSuchElementException ex) {
    List<String> errorMessage = List.of(notFoundMessage);
    return ErrorResponse.forNotFoundError(errorMessage);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public @ResponseBody ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    List<String> errorMessage = List.of(conflictMessage);
    return ErrorResponse.forConflictError(errorMessage);
  }
}
