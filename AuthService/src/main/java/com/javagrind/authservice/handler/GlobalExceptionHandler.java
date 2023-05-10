package com.javagrind.authservice.handler;

import com.javagrind.authservice.dto.Response;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
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

    @ExceptionHandler(JwtException.class)
    public static ResponseEntity<Response<Object>> handleJWTException(JwtException ex) {
        response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public static ResponseEntity<Response<Object>> handleJWTSignatureException(io.jsonwebtoken.security.SignatureException ex) {
        response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public static ResponseEntity<Response<Object>> handleJWTMalformedException(MalformedJwtException ex) {
        response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public static ResponseEntity<Response<Object>> handleJWTExpiredException(ExpiredJwtException ex) {
        response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public static ResponseEntity<Response<Object>> handleUnsupportedJWTException(UnsupportedJwtException ex) {
        response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public static ResponseEntity<Response<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, "Wrong Username or Password", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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
        String errorMessage = "Internal Server Error";
        Response<Object> response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, errorMessage, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
