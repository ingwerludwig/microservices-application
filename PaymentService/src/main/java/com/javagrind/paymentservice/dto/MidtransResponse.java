package com.javagrind.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@AllArgsConstructor
@RequestMapping
public class MidtransResponse {
    private String order_id;
    private String payment_url;
}
