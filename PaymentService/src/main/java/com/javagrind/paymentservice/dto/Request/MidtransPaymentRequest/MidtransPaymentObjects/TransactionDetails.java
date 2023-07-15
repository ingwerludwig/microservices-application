package com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransPaymentObjects;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TransactionDetails {

    @NotEmpty(message = "order_id cannot be empty")
    private String order_id;

    @NotEmpty(message = "gross_amount cannot be empty")
    private Integer gross_amount;
}
