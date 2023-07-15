package com.javagrind.paymentservice.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.paymentservice.dto.MidtransResponse;
import com.javagrind.paymentservice.dto.PaymentResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import com.javagrind.paymentservice.dto.Response;
import com.javagrind.paymentservice.service.PaymentService;
import com.javagrind.paymentservice.serviceClient.MidtransServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final MidtransServiceClient midtransServiceClient;

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

    @Override
    @CircuitBreaker(name = "Payment-Service", fallbackMethod = "fallbackPayment")
    @TimeLimiter(name = "Payment-Service")
    public CompletableFuture<Response<PaymentResponse>> pay(MidtransChargeRequest request) {
        CompletableFuture<Response<MidtransResponse>> service =
                CompletableFuture.supplyAsync(() -> {
                    try   {return midtransServiceClient.charge(request);}
                    catch (JsonProcessingException e) {throw new RuntimeException(e);}
                });

        return service.thenApplyAsync(result -> {
            if (result.getStatusCode() == HttpStatus.OK.value() && result.getData() != null) {
                MidtransResponse dataObject = result.getData();

                PaymentResponse newOrder = new PaymentResponse(LocalDateTime.now(),dataObject.getPayment_url());
                LOGGER.info("Payment has been created \n" + newOrder+ "\n");
                return new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Create Payment successfully", newOrder);

            }else if(result.getStatusCode() == HttpStatus.NOT_FOUND.value() && result.getData() == null) {
                LOGGER.info("Payment failed to created \n" + result.getMessage() + "\n");
                return new Response<>(result.getStatusCode(), result.getSuccess(), result.getMessage(), null);

            }else{
                throw new RuntimeException(result.getMessage());
            }
        });
    }

    public CompletableFuture<Response<PaymentResponse>> fallbackPayment(MidtransChargeRequest request, Throwable e) {
        LOGGER.error("Payment failed: " + e);
        String errorMessage = "Create Payment failed, try again later. Error caused : " + e.getMessage();
        Response<PaymentResponse> fallbackResponse = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, errorMessage, null);
        return CompletableFuture.completedFuture(fallbackResponse);
    }
}
