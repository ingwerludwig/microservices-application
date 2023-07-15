package com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransPaymentObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Terms {
    private String[] bni;
    private String[] mandiri;
    private String[] cimb;
    private String[] bca;
    private String[] offline;
}
