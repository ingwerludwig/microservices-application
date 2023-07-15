package com.javagrind.paymentservice.controller;

import com.javagrind.paymentservice.dto.PaymentResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import com.javagrind.paymentservice.dto.Response;
import com.javagrind.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {

    private static final Logger logger = Logger.getLogger(PaymentController.class.getName());
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<Response<PaymentResponse>> creeatePayment (@Valid @RequestBody MidtransChargeRequest request){
        CompletableFuture<Response<PaymentResponse>> newPayment = paymentService.pay(request);
        Response<PaymentResponse> result = newPayment.join();

        HttpStatus httpStatus = HttpStatus.valueOf(result.getStatusCode());
        return ResponseEntity.status(httpStatus).body(result);
    }
}
