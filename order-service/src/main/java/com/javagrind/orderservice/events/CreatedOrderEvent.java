package com.javagrind.orderservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CreatedOrderEvent {
    private UUID id;
    private String productId;
    private String user_id;
    private String productName;
    private String description;
    private Long amounts;
    private Long price;
    private LocalDateTime expiredOn;
}
