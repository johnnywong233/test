package com.johnny.validator.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice(assignableTypes = TaskController2.class)
public class TaskController2Advice extends ResponseEntityExceptionHandler {

    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        ValidationError error = ValidationErrorBuilder.fromBindingErrors(exception.getBindingResult());
        return Objects.requireNonNull(super.handleExceptionInternal(exception, error, headers, status, request));
    }
}