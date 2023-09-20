package com.javagrind.orderservice.services.impl;

import com.javagrind.orderservice.dto.request.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.FindOrderRequest;
import com.javagrind.orderservice.dto.request.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;
import com.javagrind.orderservice.events.CreatedOrderEvent;
import com.javagrind.orderservice.repositories.OrderRepository;
import com.javagrind.orderservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);
    @Override
    @Transactional
    @CircuitBreaker(name = "Order-Service", fallbackMethod = "fallbackOrder")
    @TimeLimiter(name = "Order-Service")
    public CompletableFuture<OrderEntity> create(CreateOrderRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Long totalPrice = request.getPrice() * request.getAmounts();
                OrderEntity newOrder = new OrderEntity(request.getProductId(), UUID.fromString(request.getUserId()), request.getProductName(), request.getDescription(), request.getAmounts(), totalPrice);
                orderRepository.save(newOrder);

                CreatedOrderEvent event = modelMapper.map(newOrder, CreatedOrderEvent.class);
                kafkaTemplate.send("order-topic", (Object) event);

                LOGGER.info("Order has been created \n" + newOrder + "\n");
                return newOrder;
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage() + "caused by : " + ex.getCause());
            }
        });
    }
    public CompletableFuture<OrderEntity> fallbackOrder(CreateOrderRequest request, Throwable e) {
        LOGGER.error("Create Order failed: " + e.getMessage());
        String errorMessage = "Create Order failed, try again later. Error caused : " + e.getMessage();

        CompletableFuture<OrderEntity> fallbackResponse = new CompletableFuture<>();
        fallbackResponse.completeExceptionally(new RuntimeException(errorMessage));

        return fallbackResponse;
    }


    @Override
    @CircuitBreaker(name = "Order-Service", fallbackMethod = "fallbackListOrder")
    @TimeLimiter(name = "Order-Service")
    public CompletableFuture<List<OrderEntity>> findOrderByUserId(FindOrderRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Optional<List<OrderEntity>> result = orderRepository.findByUserId(request.getUserId());
                if (Boolean.TRUE.equals(result.map(products -> !products.isEmpty()))) {
                    LOGGER.info("Order has been found \n" + result + "\n");
                    return result.get();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage() + "caused by : " + ex.getCause());
            }
            return null;
        });
    }
    public CompletableFuture<List<OrderEntity>> fallbackListOrder(FindOrderRequest request, Throwable e) {
        LOGGER.error("Get Order failed: " + e.getMessage());
        String errorMessage = "Get Order failed, try again later. Error caused : " + e.getMessage();

        CompletableFuture<List<OrderEntity>> fallbackResponse = new CompletableFuture<>();
        fallbackResponse.completeExceptionally(new RuntimeException(errorMessage));

        return fallbackResponse;
    }


    @Override
    @CircuitBreaker(name = "Order-Service", fallbackMethod = "fallbackGetOrder")
    @TimeLimiter(name = "Order-Service")
    public CompletableFuture<OrderEntity> findOrderById(String orderId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Optional<OrderEntity> requestedOrder = orderRepository.findById(UUID.fromString(orderId));
                if (requestedOrder.isPresent() && requestedOrder.get().getId().equals(UUID.fromString(orderId))){
                    OrderEntity requestedEntity = requestedOrder.get();
                    LOGGER.info("Order has been found \n" + requestedEntity+ "\n");
                    return requestedEntity;
                }
            } catch (Exception ex) {
                throw new NoSuchElementException(ex.getMessage() + "caused by : " + ex.getCause());
            }
            return null;
        });
    }
    public CompletableFuture<OrderEntity> fallbackGetOrder(String orderId, Throwable e) {
        LOGGER.error("Get Order failed: " + e.getMessage());
        String errorMessage = "Get Order failed, try again later. Error caused : " + e.getMessage();

        CompletableFuture<OrderEntity> fallbackResponse = new CompletableFuture<>();
        fallbackResponse.completeExceptionally(new RuntimeException(errorMessage));

        return fallbackResponse;
    }


    @Override
    @Transactional
    @CircuitBreaker(name = "Order-Service", fallbackMethod = "fallbackUpdateOrder")
    @TimeLimiter(name = "Order-Service")
    public CompletableFuture<OrderEntity> update(String orderId, String userId, UpdateOrderRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Optional<OrderEntity> requestedOrder = orderRepository.findById(orderId);
                if (requestedOrder.isPresent() && requestedOrder.get().getId().equals(orderId)){
                    modelMapper.map(request, requestedOrder.get());
                    OrderEntity savedEntity = orderRepository.save(requestedOrder.get());
                    LOGGER.info("Order has been updated \n" + savedEntity+ "\n");
                    return savedEntity;
                }
            } catch (Exception ex) {
                throw new NoSuchElementException(ex.getMessage() + "caused by : " + ex.getCause());
            }
            return null;
        });
    }

    public CompletableFuture<OrderEntity> fallbackUpdateOrder(String orderId, String userId, UpdateOrderRequest request, Throwable e) {
        LOGGER.error("Update Order failed: " + e.getMessage());
        String errorMessage = "Update Order failed, try again later. Error caused : " + e.getMessage();

        CompletableFuture<OrderEntity> fallbackResponse = new CompletableFuture<>();
        fallbackResponse.completeExceptionally(new RuntimeException(errorMessage));

        return fallbackResponse;
    }

}