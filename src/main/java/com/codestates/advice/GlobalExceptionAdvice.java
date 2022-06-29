package com.codestates.advice;

import com.codestates.exception.BusinessLogicException;
import com.codestates.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {
        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
//        System.out.println(e.getExceptionCode().getStatus());
//        System.out.println(e.getMessage());

        // TODO GlobalExceptionAdvice 기능 추가 1
        // return new ResponseEntity<>(HttpStatus.valueOf(e.getExceptionCode()
                //.getStatus()));

        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    // TODO GlobalExceptionAdvice 기능 추가 2
    @ExceptionHandler
    public ResponseEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED);
    }


    // TODO GlobalExceptionAdvice 기능 추가 3

    @ExceptionHandler
    public ResponseEntity handleException(NullPointerException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
