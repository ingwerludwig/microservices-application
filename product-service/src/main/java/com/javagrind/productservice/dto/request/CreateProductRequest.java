package com.javagrind.productservice.dto.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
