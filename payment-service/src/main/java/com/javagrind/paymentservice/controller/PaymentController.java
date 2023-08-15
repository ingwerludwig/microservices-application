package com.javagrind.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.paymentservice.dto.PaymentResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransWebhookRequest;
import com.javagrind.paymentservice.dto.Response;
import com.javagrind.paymentservice.dto.UpdateOrderDao;
import com.javagrind.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public Mono<ResponseEntity<Response<PaymentResponse>>> createPayment(@Valid @RequestBody MidtransChargeRequest request) throws JsonProcessingException {
        return paymentService.pay(request)
                .map(paymentResponse -> ResponseEntity.status(paymentResponse.getStatusCode())
                                                      .body(paymentResponse));
    }

    @PostMapping("/pay_webhook")
    public Mono<ResponseEntity<Response<UpdateOrderDao>>> notifyPay(@Valid @RequestBody MidtransWebhookRequest request){
        return paymentService.notify(request)
                .map(webhookResponse -> ResponseEntity.status(webhookResponse.getStatusCode())
                                                      .body(webhookResponse));
    }
}
