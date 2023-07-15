package com.javagrind.paymentservice.serviceClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.paymentservice.dto.MidtransResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import com.javagrind.paymentservice.dto.Response;

public interface MidtransServiceClient {
    Response<MidtransResponse> charge(MidtransChargeRequest request)throws JsonProcessingException;
}
