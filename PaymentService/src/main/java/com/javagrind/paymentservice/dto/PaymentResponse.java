package com.javagrind.paymentservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    @NotEmpty(message = "timestamp cannot be empty")
    private LocalDateTime payment_created= LocalDateTime.now();

    @NotEmpty(message = "payment_link_url cannot be empty")
    private String payment_link_url;
}
