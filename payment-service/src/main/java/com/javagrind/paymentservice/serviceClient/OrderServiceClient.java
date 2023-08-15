package com.javagrind.paymentservice.serviceClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransWebhookRequest;
import com.javagrind.paymentservice.dto.UpdateOrderDao;
import reactor.core.publisher.Mono;

public interface OrderServiceClient {
    Mono<UpdateOrderResponse> updateOrderWebhook(MidtransWebhookRequest request)throws JsonProcessingException;
}
