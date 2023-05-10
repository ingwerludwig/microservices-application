package com.javagrind.orderservice.serviceClient.ProductServiceClient;

import com.javagrind.orderservice.dto.Response;
import org.springframework.http.ResponseEntity;

public interface ProductServiceClient {
    ResponseEntity<Response<Object>> findProduct(String productId);
}
