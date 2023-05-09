package com.javagrind.orderservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@RequiredArgsConstructor
public class CreateOrderRequest {

    @NotEmpty(message = "productId cannot be empty")
    private String productId;

    @NotEmpty(message = "userId cannot be empty")
    private String userId;

    private String description;

    @NotNull(message = "amounts cannot be null")
    @Min(value = 1, message = "amounts must be greater than 0")
    private Long amounts;
}
