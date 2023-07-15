package com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransPaymentObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Expiry {
    private String start_time;
    //"start_time": "2023-07-16 18:00 +0700"

    private Integer duration;
    //days

    private String days="days";
}
