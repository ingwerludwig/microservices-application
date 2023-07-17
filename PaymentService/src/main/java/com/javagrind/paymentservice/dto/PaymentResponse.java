package com.javagrind.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private LocalDateTime payment_created= LocalDateTime.now();
    private String payment_link_url;
}
