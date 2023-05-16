package com.javagrind.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.request.Order.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.Order.FindOrderRequest;
import com.javagrind.orderservice.dto.request.Order.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;

public interface OrderService {

    OrderEntity create(CreateOrderRequest request) throws JsonProcessingException;

    Object findOrder(FindOrderRequest request);

    OrderEntity update(String orderId, String userId, UpdateOrderRequest request);
}
