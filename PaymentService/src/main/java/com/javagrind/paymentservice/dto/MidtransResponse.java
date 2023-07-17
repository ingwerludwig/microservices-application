package com.javagrind.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@AllArgsConstructor
@RequestMapping
public class MidtransResponse {
    @JsonProperty("order_id")
    private String order_id;

    @JsonProperty("payment_url")
    private String payment_url;
}
