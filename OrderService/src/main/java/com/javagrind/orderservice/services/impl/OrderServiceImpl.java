package com.javagrind.orderservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.Order.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.Order.FindOrderRequest;
import com.javagrind.orderservice.dto.request.Order.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;
import com.javagrind.orderservice.repositories.OrderRepository;
import com.javagrind.orderservice.serviceClient.ProductServiceClient.ProductDao;
import com.javagrind.orderservice.serviceClient.ProductServiceClient.ProductServiceClient;
import com.javagrind.orderservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductServiceClient productServiceClient;

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);
    @Override
    @Transactional
    @CircuitBreaker(name = "Order-Service", fallbackMethod = "fallbackOrder")
    @TimeLimiter(name = "Order-Service")
    public CompletableFuture<Response<OrderEntity>> create(CreateOrderRequest request){
        CompletableFuture<Response<ProductDao>> service =
                CompletableFuture.supplyAsync(() -> {
                    try   {return productServiceClient.findProduct(request.getProductId());}
                    catch (JsonProcessingException e) {throw new RuntimeException(e);}
                });

        return service.thenApplyAsync(result -> {
            if (result.getStatusCode() == HttpStatus.OK.value() && result.getData() != null) {
                ProductDao dataObject = result.getData();

                OrderEntity newOrder = new OrderEntity(dataObject.getId(), request.getUserId(), dataObject.getProductName(), request.getDescription(), request.getAmounts(), dataObject.getPrice() * request.getAmounts());
                orderRepository.save(newOrder);
                LOGGER.info("Order has been created \n" + newOrder+ "\n");
                return new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Create order successfully", newOrder);

            }else if(result.getStatusCode() == HttpStatus.NOT_FOUND.value() && result.getData() == null) {
                LOGGER.info("Order failed to created \n" + result.getMessage() + "\n");
                return new Response<>(result.getStatusCode(), result.getSuccess(), result.getMessage(), null);

            }else{
                throw new RuntimeException(result.getMessage());
            }
        });
    }

    @Override
    @CircuitBreaker(name = "Order-Service", fallbackMethod = "fallbackListOrder")
    public Response<List<OrderEntity>> findOrder(FindOrderRequest request) {
        Optional<List<OrderEntity>> result = orderRepository.findByUserId(request.getUserId());

        if (Boolean.TRUE.equals(result.map(products -> !products.isEmpty()))) {
            LOGGER.info("Order has been found \n" + result+ "\n");
            return new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Find order successfully", result.get());

        }else {
            LOGGER.error("Order failed to created \n" + result + "\n");
            return new Response<>(HttpStatus.NOT_FOUND.value(), Boolean.FALSE, "Order Not Found", null);
        }
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "Order-Service", fallbackMethod = "fallbackUpdateOrder")
    public Response<OrderEntity> update(String orderId, String userId, UpdateOrderRequest request) {
        Optional<OrderEntity> requestedOrder = orderRepository.findById(orderId);

        if (requestedOrder.isPresent() && requestedOrder.get().getId().equals(orderId)){
            modelMapper.map(request, requestedOrder.get());
            OrderEntity savedEntity = orderRepository.save(requestedOrder.get());
            LOGGER.info("Order has been updated \n" + savedEntity+ "\n");
            return new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Find order successfully", savedEntity);

        }else{
            LOGGER.error("Order failed to updated \n" + "Order Not Found" + "\n");
            return new Response<>(HttpStatus.NOT_FOUND.value(), Boolean.FALSE, "Order Not Found", null);
        }
    }


    public CompletableFuture<Response<OrderEntity>> fallbackOrder(CreateOrderRequest request, Throwable e) {
        LOGGER.error("Create Order failed: " + e);
        String errorMessage = "Create Order failed, try again later. Error caused : " + e.getMessage();
        Response<OrderEntity> fallbackResponse = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, errorMessage, null);
        return CompletableFuture.completedFuture(fallbackResponse);
    }

    public Response<OrderEntity> fallbackUpdateOrder(String orderId, String userId, UpdateOrderRequest request, Throwable e) {
        LOGGER.error("Update Order failed: " + e);
        String errorMessage = "Update Order failed, try again later. Error caused : " + e.getMessage();
        Response<OrderEntity> fallbackResponse = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, errorMessage, null);
        return fallbackResponse;
    }

    public Response<List<OrderEntity>> fallbackListOrder(FindOrderRequest request, Throwable e) {
        LOGGER.error("Find Order failed: " + e);
        String errorMessage = "Find Order failed, try again later. Error caused : " + e.getMessage();
        Response<List<OrderEntity>> fallbackResponse = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, errorMessage, null);
        return fallbackResponse;
    }

    //    @PutMapping("/update")
//    @CircuitBreaker(name = "Order-Service", fallbackMethod = "fallback")
//    @TimeLimiter(name = "Order-Service")
//    public CompletableFuture<ResponseEntity<Response<Object>>> updateOrder(@RequestParam String orderId, @RequestParam String userId, @RequestBody UpdateOrderRequest request, BindingResult errors) {
//        CompletableFuture<Object> service = CompletableFuture
//              .supplyAsync(() -> orderService.update(orderId, userId, request))
//              .thenApplyAsync(updateEntity -> new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Update order successfully", updateEntity));
//
//        return service.thenApplyAsync(result -> {
//            Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Update order successfully", result);
//            return ResponseEntity.ok().body(response);
//        });
//    }

//    public CompletableFuture<ResponseEntity<Response<Object>>> fallback(@RequestParam String orderId, @RequestParam String userId, @RequestBody UpdateOrderRequest request, BindingResult errors, TimeoutException ex) {
//        // fetch results from the cache
//        Response<Object> response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE,ex.getMessage(),null)
//        return CompletableFuture.supplyAsync(() -> ResponseEntity.internalServerError().body(response));
//    }
}
