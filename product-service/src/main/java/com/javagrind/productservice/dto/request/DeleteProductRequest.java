package com.javagrind.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class DeleteProductRequest {

    @NotBlank
    private String productId;
}
