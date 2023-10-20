package com.javagrind.orderservice.dto.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Validated
public class OrderWorkflowDTO {
    @NotEmpty(message = "productId cannot be empty")
    private String productId;

    @NotEmpty(message = "userId cannot be empty")
    private String userId;

    @NotEmpty(message = "description cannot be empty")
    private String description;

    @Min(value = 1, message = "amounts must be greater than 0")
    private Long amounts;
}
