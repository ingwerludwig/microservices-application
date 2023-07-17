package com.javagrind.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.paymentservice.dto.PaymentResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import com.javagrind.paymentservice.dto.Response;
import com.javagrind.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
}
