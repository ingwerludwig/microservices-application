package com.javagrind.paymentservice.dto.Request.OrderServiceClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateProductRequest {
    private String paymentStatus;
    private String description;
}
