package com.javagrind.orderservice.dto.request;

import com.javagrind.orderservice.entity.OrderPayment;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateOrderRequest {
    private OrderPayment paymentStatus;
    private String description;
}
