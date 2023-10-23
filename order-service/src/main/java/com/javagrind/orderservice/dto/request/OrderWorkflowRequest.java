package com.javagrind.orderservice.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class OrderWorkflowRequest {
    private Map<String, Object> jsonData = new HashMap<>();
    public Map<String, Object> getJsonData(OrderWorkflowDTO request, String token, String baseUrl){
        this.jsonData.put("productId", request.getProductId());
        this.jsonData.put("userId", request.getUserId());
        this.jsonData.put("description", request.getDescription());
        this.jsonData.put("amounts", request.getAmounts());
        this.jsonData.put("token", token);
        this.jsonData.put("baseUrl",baseUrl);
        return jsonData;
    }
}
