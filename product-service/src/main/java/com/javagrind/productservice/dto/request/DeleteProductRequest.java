package com.javagrind.productservice.dto.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class DeleteProductRequest {

    @NotBlank
    private String productId;
}
