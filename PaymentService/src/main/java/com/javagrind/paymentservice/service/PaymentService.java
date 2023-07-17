package com.javagrind.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.paymentservice.dto.PaymentResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import com.javagrind.paymentservice.dto.Response;
import reactor.core.publisher.Mono;

public interface PaymentService {
    Mono<Response<PaymentResponse>> pay(MidtransChargeRequest request) throws JsonProcessingException;
}