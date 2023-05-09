package com.javagrind.orderservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@RequiredArgsConstructor
public class FindOrderRequest {

    @NotEmpty(message = "userId cannot be empty")
    private String userId;

}
