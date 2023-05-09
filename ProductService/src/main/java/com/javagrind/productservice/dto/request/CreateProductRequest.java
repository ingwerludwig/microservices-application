package com.javagrind.productservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class CreateProductRequest {

    @NotBlank
    private String productName;

    @Min(1)
    private Long price;

    private String description;

    @Min(1)
    private Long amounts;
}
