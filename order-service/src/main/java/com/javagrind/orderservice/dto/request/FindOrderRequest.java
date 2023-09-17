package com.javagrind.orderservice.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@RequiredArgsConstructor
public class FindOrderRequest {

    @NotEmpty(message = "userId cannot be empty")
    private String userId;

}
