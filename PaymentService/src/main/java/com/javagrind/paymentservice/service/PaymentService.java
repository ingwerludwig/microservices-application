package com.javagrind.paymentservice.service;

import com.javagrind.paymentservice.dto.MidtransResponse;
import com.javagrind.paymentservice.dto.PaymentResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import com.javagrind.paymentservice.dto.Response;
import java.util.concurrent.CompletableFuture;

public interface PaymentService {

    CompletableFuture<Response<PaymentResponse>> pay(MidtransChargeRequest request);

}
