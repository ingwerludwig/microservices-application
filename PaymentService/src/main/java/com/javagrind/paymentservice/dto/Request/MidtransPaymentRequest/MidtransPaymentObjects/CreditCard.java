package com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransPaymentObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CreditCard {
    private Boolean secure=Boolean.TRUE;
    private String bank;
    private Installment installment;
}
