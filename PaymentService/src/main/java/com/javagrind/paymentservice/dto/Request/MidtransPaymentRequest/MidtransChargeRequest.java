package com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest;

import com.javagrind.paymentservice.dao.CustomerDao;
import com.javagrind.paymentservice.dao.OrderDao;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransPaymentObjects.CreditCard;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransPaymentObjects.Expiry;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransPaymentObjects.TransactionDetails;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MidtransChargeRequest {

    @NotEmpty(message = "transaction_details cannot be empty")
    private TransactionDetails transaction_details;

    private Boolean customer_required=Boolean.TRUE;

    private CreditCard credit_card;

    private Integer usage_limit;

    private Expiry expiry;

    private String[] enabled_payments = {"credit_card", "bca_va", "indomaret"};

    private ArrayList<OrderDao> item_details;

    private CustomerDao customer_details;
}
