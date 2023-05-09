package com.javagrind.orderservice.services;

import com.javagrind.orderservice.dto.request.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.FindOrderRequest;
import com.javagrind.orderservice.dto.request.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;

public interface OrderService {

    OrderEntity create(CreateOrderRequest request);

    Object findOrder(FindOrderRequest request);

    OrderEntity update(String orderId, String userId, UpdateOrderRequest request);
}
