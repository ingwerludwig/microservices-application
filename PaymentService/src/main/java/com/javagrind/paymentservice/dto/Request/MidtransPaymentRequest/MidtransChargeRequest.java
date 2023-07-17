package com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransPaymentObjects.TransactionDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class MidtransChargeRequest {
    @JsonProperty("transaction_details")
    private TransactionDetails transaction_details;
}
