package com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MidtransWebhookRequest {
    @JsonProperty("transaction_time")
    private String transaction_time;

    @JsonProperty("transaction_id")
    private String transaction_id;

    @JsonProperty("status_message")
    private String status_message;

    @JsonProperty("status_Code")
    private String status_Code;

    @JsonProperty("signature_key")
    private String signature_key;

    @JsonProperty("payment_type")
    private String payment_type;

    @JsonProperty("order_id")
    private String order_id;

    @JsonProperty("merchant_id")
    private String merchant_id;

    @JsonProperty("masked_card")
    private String masked_card;

    @JsonProperty("gross_amount")
    private String gross_amount;

    @JsonProperty("fraud_status")
    private String fraud_status;

    @JsonProperty("eci")
    private String eci;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("channel_response_message")
    private String channel_response_message;

    @JsonProperty("channel_response_code")
    private String channel_response_code;

    @JsonProperty("card_type")
    private String card_type;

    @JsonProperty("bank")
    private String bank;

    @JsonProperty("approval_code")
    private String approval_code;
}
