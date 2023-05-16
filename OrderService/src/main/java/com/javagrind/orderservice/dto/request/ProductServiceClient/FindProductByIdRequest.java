package com.javagrind.orderservice.dto.request.ProductServiceClient;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@RequiredArgsConstructor
@AllArgsConstructor
public class FindProductByIdRequest {

    @NotEmpty(message = "productId cannot be empty")
    @JsonProperty("productId")
    private String productId;

}
