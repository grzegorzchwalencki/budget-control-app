package com.MyApp.budgetControl.api;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GlobalExceptionHandlerTest {

    @Test
    @SneakyThrows
    void handleMethodArgumentNotValidException() {
        try {
            BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "ErrorResponse");
            bindingResult.addError(new FieldError("ErrorResponse", "errorDetails", "Cost value should be positive"));
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        } catch (Exception ex) {
            GlobalExceptionHandler handler = new GlobalExceptionHandler();
            ErrorResponse response = handler.handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex);

            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
            assertTrue(response.getErrorDetails().contains("Cost value should be positive"));
            assertEquals(ErrorResponse.ErrorType.VALIDATION_ERROR, response.getErrorType());
        }
    }

    @Test
    void handleUnexpectedErrors() {
        try {
            throw new Exception("This is unhandled error");
        } catch (Exception ex) {
            GlobalExceptionHandler handler = new GlobalExceptionHandler();
            ErrorResponse response = handler.handleUnexpectedErrors(ex);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
            assertTrue(response.getErrorDetails().contains("Unknown error occured"));
            assertEquals(ErrorResponse.ErrorType.UNHANDLED_ERROR, response.getErrorType());
        }
    }

    @Test
    void handleNoSuchElementExceptions() {
        try {
            throw new NoSuchElementException("No such element");
        } catch (NoSuchElementException ex) {
            GlobalExceptionHandler handler = new GlobalExceptionHandler();
            ErrorResponse response = handler.handleNoSuchElementExceptions(ex);

            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
            assertTrue(response.getErrorDetails().contains("Expense with given Id does not exist"));
            assertEquals(ErrorResponse.ErrorType.NOT_FOUND_ERROR, response.getErrorType());
        }
    }

}
