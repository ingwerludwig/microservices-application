package com.javagrind.orderservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.Order.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.Order.FindOrderRequest;
import com.javagrind.orderservice.dto.request.Order.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;
import com.javagrind.orderservice.repositories.OrderRepository;
import com.javagrind.orderservice.serviceClient.ProductServiceClient.ProductServiceClient;
import com.javagrind.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductServiceClient productServiceClient;

    @Override
    @Transactional
    public OrderEntity create(CreateOrderRequest request) throws JsonProcessingException {
        Response<Object> serviceResponse = productServiceClient.findProduct(request.getProductId());

        if (serviceResponse.getStatusCode() == 200 && serviceResponse.getData() != null) {
            OrderEntity newOrder = new OrderEntity(request.getProductId(), request.getUserId(), "TEST", request.getDescription(), request.getAmounts(), 10000L * request.getAmounts());
            orderRepository.save(newOrder);
            return newOrder;
        } else {throw new RuntimeException(serviceResponse.getMessage());}
    }

    @Override
    public Object findOrder(FindOrderRequest request) {
        Optional<List<OrderEntity>> result = orderRepository.findByUserId(request.getUserId());

        if (result.map(products -> !products.isEmpty()).orElse(null))  return result;
        else return null;
    }

    @Override
    @Transactional
    public OrderEntity update(String orderId, String userId, UpdateOrderRequest request) {
        Optional<OrderEntity> requestedOrder = orderRepository.findById(orderId);

        if (requestedOrder.isPresent() && requestedOrder.get().getId().equals(orderId)){
            modelMapper.map(request, requestedOrder.get());
            OrderEntity savedEntity = orderRepository.save(requestedOrder.get());
            return savedEntity;
        }else{ throw new NoSuchElementException("Order not found");}
    }
}
