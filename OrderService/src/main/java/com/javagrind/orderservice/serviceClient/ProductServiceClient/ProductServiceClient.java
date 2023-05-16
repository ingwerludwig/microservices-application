package com.javagrind.orderservice.serviceClient.ProductServiceClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.Response;

public interface ProductServiceClient {
    Response<Object> findProduct (String productId) throws JsonProcessingException;
}
