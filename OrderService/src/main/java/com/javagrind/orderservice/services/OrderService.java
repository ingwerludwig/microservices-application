package com.javagrind.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.Order.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.Order.FindOrderRequest;
import com.javagrind.orderservice.dto.request.Order.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;
import com.javagrind.orderservice.serviceClient.ProductServiceClient.ProductDao;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {

    CompletableFuture<Response<OrderEntity>> create(CreateOrderRequest request) throws JsonProcessingException;

    List<OrderEntity> findOrder(FindOrderRequest request);

    OrderEntity update(String orderId, String userId, UpdateOrderRequest request);
}
