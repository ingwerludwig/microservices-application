package com.javagrind.orderservice.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Validated
@RequiredArgsConstructor
public class CreateOrderRequest {

    @NotEmpty(message = "productId cannot be empty")
    private String productId;

    @NotEmpty(message = "userId cannot be empty")
    private String userId;

    @NotEmpty(message = "productName cannot be empty")
    private String productName;

    @Min(value = 1, message = "amounts must be greater than 0")
    private Long price;

    private String description;

    @Min(value = 1, message = "amounts must be greater than 0")
    private Long amounts;
}
