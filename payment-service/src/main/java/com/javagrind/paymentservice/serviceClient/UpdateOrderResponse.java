package com.javagrind.paymentservice.serviceClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateOrderResponse {

    private String id;

    private String productId;

    private String user_id;

    private String productName;

    private String description;

    private Long amounts;

    private Long price;

    private String paymentStatus;

    private LocalDateTime expiredOn;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
