package com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransPaymentObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Validated
public class TransactionDetails {
    @JsonProperty("order_id")
    private String order_id;

    @JsonProperty("gross_amount")
    private Integer gross_amount;
}
