package com.javagrind.productservice.dto.request;

import lombok.Data;

@Data
public class UpdateProductRequest {
    private String productName;

    private Long price;

    private String description;

    private Long amounts;

}
