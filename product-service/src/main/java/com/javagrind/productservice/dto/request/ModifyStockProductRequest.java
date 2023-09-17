package com.javagrind.productservice.dto.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class ModifyStockProductRequest {

    @NotBlank
    private Long amounts;
}