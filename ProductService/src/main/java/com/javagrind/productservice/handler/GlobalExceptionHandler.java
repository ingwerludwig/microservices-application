package com.javagrind.productservice.handler;

import com.javagrind.productservice.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.stream.Collectors;


@ControllerAdvice
public class BadRequestExceptionHandler {
    static Response<Object> response;
    public BadRequestExceptionHandler() {
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public static ResponseEntity<Response<Object>> handleBadRequest(Errors errors) {

        String errorMessages = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
        response = new Response<>(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE, errorMessages, null);
        System.err.println(errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public static ResponseEntity<Response<Object>> handleInternalServerError(Errors errors) {

        String errorMessages = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
        response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, errorMessages, null);
        System.err.println(errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
