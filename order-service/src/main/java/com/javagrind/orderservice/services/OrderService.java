package com.javagrind.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.request.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.FindOrderRequest;
import com.javagrind.orderservice.dto.request.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {

    CompletableFuture<OrderEntity> create(CreateOrderRequest request) throws Exception;

    CompletableFuture<List<OrderEntity>> findOrderByUserId(FindOrderRequest request);

    CompletableFuture<OrderEntity> findOrderById(String orderId);

    CompletableFuture<OrderEntity> update(String orderId, String userId, UpdateOrderRequest request);
}
