package com.javagrind.paymentservice.serviceClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.paymentservice.dto.MidtransResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import reactor.core.publisher.Mono;

public interface MidtransServiceClient {
    Mono<MidtransResponse> charge(MidtransChargeRequest request)throws JsonProcessingException;
}
