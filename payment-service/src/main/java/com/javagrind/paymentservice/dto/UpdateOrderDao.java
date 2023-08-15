package com.javagrind.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateOrderDao {
    private String transaction_time;
    private String transaction_id;
    private String status_message;
    private String status_Code;
    private String signature_key;
    private String payment_type;
    private String order_id;
    private String merchant_id;
    private String masked_card;
    private String gross_amount;
    private String fraud_status;
    private String eci;
    private String currency;
    private String channel_response_message;
    private String channel_response_code;
    private String card_type;
    private String bank;
    private String approval_code;
}
