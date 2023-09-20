package com.javagrind.paymentservice.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.paymentservice.dto.PaymentResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransWebhookRequest;
import com.javagrind.paymentservice.dto.Response;
import com.javagrind.paymentservice.dto.UpdateOrderDao;
import com.javagrind.paymentservice.service.PaymentService;
import com.javagrind.paymentservice.serviceClient.MidtransServiceClientImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final MidtransServiceClientImpl midtransServiceClient;

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

    @Override
    @CircuitBreaker(name = "Payment-Service", fallbackMethod = "fallbackPayment")
    @TimeLimiter(name = "Payment-Service")
    public Mono<Response<PaymentResponse>> pay(MidtransChargeRequest request) throws Exception {
        return midtransServiceClient.charge(request)
                .flatMap(midtransResponse -> {
                    PaymentResponse paymentResponse = new PaymentResponse(LocalDateTime.now(), midtransResponse.getPayment_url(), midtransResponse.getOrder_id());
                    LOGGER.info("Payment response: " + midtransResponse);
                    return Mono.just(new Response<>(HttpStatus.OK.value(), Boolean.FALSE, "Payment Successful", paymentResponse));
                })
                .onErrorResume(error -> {
                    LOGGER.error("Payment failed: " + error.getMessage());
                    return Mono.just(new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, "Payment unsuccessful", null));
                });
    }

    @Override
    public Mono<Response<UpdateOrderDao>> notify(MidtransWebhookRequest request) {
        return null;
    }
}
