package com.MyApp.budgetControl.api


import org.springframework.core.MethodParameter
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import spock.lang.Shared
import spock.lang.Specification

class GlobalExceptionHandlerTest extends Specification {

    def notFoundMessage = "Element with given Id does not exist"
    def unknownErrorMessage = "Unknown error occurred"
    def conflictErrorMessage = "Name is already used. Please choose a different one"

    @Shared
    GlobalExceptionHandler handler = new GlobalExceptionHandler()

    def "should catch and handle MethodArgumentNotValidException"() {
        given:
            BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "ErrorResponse")
            bindingResult.addError(
                    new FieldError("ErrorResponse", "errorDetails", "Cost value should be positive")
            )

            MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
                    new MethodParameter(getClass().getDeclaredMethods()[0], -1), bindingResult
            )

        when:
            ErrorResponse response = handler.handleMethodArgumentNotValidException(exception)

        then:
            response.statusCode == HttpStatus.BAD_REQUEST.value()
            response.errorDetails.contains("Cost value should be positive")
            response.errorType == ErrorResponse.ErrorType.VALIDATION_ERROR

    }

    def "should catch and handle UnexpectedError"() {
        given:
            Exception exception = new Exception("This is unhandled error")

        when:
            ErrorResponse response = handler.handleUnexpectedErrors(exception)

        then:
            response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()
            response.errorDetails.contains(unknownErrorMessage)
            response.errorType == ErrorResponse.ErrorType.UNHANDLED_ERROR
    }

    def "should catch and handle NoSuchElementExceptions"() {
        given:
            NoSuchElementException exception = new NoSuchElementException("No such element")

        when:
            ErrorResponse response = handler.handleNoSuchElementExceptions(exception)

        then:
            response.statusCode == HttpStatus.NOT_FOUND.value()
            response.errorDetails.contains(notFoundMessage)
            response.errorType == ErrorResponse.ErrorType.NOT_FOUND_ERROR
    }

    def "should catch and handle DataIntegrityViolationException"() {
        given:
            DataIntegrityViolationException exception = new DataIntegrityViolationException("No such element")

        when:
            ErrorResponse response = handler.handleDataIntegrityViolationException(exception)

        then:
            response.statusCode == HttpStatus.CONFLICT.value()
            response.errorDetails.contains(conflictErrorMessage)
            response.errorType == ErrorResponse.ErrorType.CONFLICT_ERROR
    }

}
