package com.javagrind.orderservice.serviceClient.ProductServiceClient;

import com.javagrind.orderservice.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ProductServiceClientImpl implements ProductServiceClient{

    private final WebClient webClient;

    @Override
    public ResponseEntity<Response<Object>> findProduct(String productId) {
        return null;
    }
}
