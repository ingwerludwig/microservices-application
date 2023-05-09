package com.javagrind.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Response<T> {
    private Integer statusCode;
    private Boolean success;
    private String message;
    private T data;
}
