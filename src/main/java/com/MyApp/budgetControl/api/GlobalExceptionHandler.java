package com.MyApp.budgetControl.api;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public @ResponseBody ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        // Get all errors
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
        List<String> errorMessage = Arrays.asList( "Unknown error occured",ex.getClass().getCanonicalName());
       return ErrorResponse.forUnhandledError(errorMessage);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ResponseStatusException.class)
    public @ResponseBody ErrorResponse handleResponseStatusExceptions (ResponseStatusException ex) {
        List<String> errorMessage = Collections.singletonList(ex.getReason());
    return  ErrorResponse.forNotFoundError(errorMessage);
    }
}
