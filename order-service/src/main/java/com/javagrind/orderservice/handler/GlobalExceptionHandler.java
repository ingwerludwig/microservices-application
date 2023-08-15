package com.javagrind.orderservice.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javagrind.orderservice.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    static Response<Object> response;
    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public static ResponseEntity<Response<Object>> handleBadRequest(Errors errors) {

        String errorMessages = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
        response = new Response<>(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE, errorMessages, null);
        System.err.println(errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public static ResponseEntity<Response<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public static ResponseEntity<Response<Object>> handleNoSuchElementException(IllegalArgumentException ex) {
        response = new Response<>(HttpStatus.NOT_FOUND.value(), Boolean.FALSE, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public static ResponseEntity<Response<Object>> handleInternalServerError(Errors errors) {

        String errorMessages = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
        response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, errorMessages, null);
        System.err.println(errorMessages);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public static ResponseEntity<Response<Object>> handleUnauthorizedRequest(Errors errors) {

        String errorMessages = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
        response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, errorMessages, null);
        System.err.println(errorMessages);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public static ResponseEntity<Response<Object>> handleForbiddenRequest(Errors errors) {

        String errorMessages = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
        response = new Response<>(HttpStatus.FORBIDDEN.value(), Boolean.FALSE, errorMessages, null);
        System.err.println(errorMessages);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleException(Exception ex) {
        Response<Object> response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<Object>> handleRuntimeException(RuntimeException ex ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object responseService = objectMapper.readValue(ex.getMessage(), new TypeReference<Response<Object>>() {});
        Response<Object> response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, "Internal Server Error on Service Call", responseService);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<Response<Object>> handleWebClientException(WebClientException ex) {
        String errorMessage = ex.getMessage();
        Response<Object> response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Call Service Error in WebClient", errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<Response<Object>> handleTimeoutException(CompletionException ex)  {
        String errorMessage = ex.getMessage();
        Response<Object> response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Request timeout, service unable to retrieve on time", errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}