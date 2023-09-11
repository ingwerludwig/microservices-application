package com.javagrind.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.Order.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.Order.FindOrderRequest;
import com.javagrind.orderservice.dto.request.Order.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;
import com.javagrind.orderservice.serviceClient.ProductServiceClient.ProductDao;
import com.javagrind.orderservice.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class.getName());
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Response<OrderEntity>> createOrder(@Valid @RequestBody CreateOrderRequest request) throws JsonProcessingException {
        CompletableFuture<Response<OrderEntity>> newOrder = orderService.create(request);
        Response<OrderEntity> result = newOrder.join();

        HttpStatus httpStatus = HttpStatus.valueOf(result.getStatusCode());
        return ResponseEntity.status(httpStatus).body(result);
    }


    @PostMapping("/getOrder")
    public ResponseEntity<Response<List<OrderEntity>>> findOrder(@Valid @RequestBody FindOrderRequest request){
        Response<List<OrderEntity>> result = orderService.findOrder(request);
        HttpStatus httpStatus = HttpStatus.valueOf(result.getStatusCode());
        return ResponseEntity.status(httpStatus).body(result);
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<Response<OrderEntity>> findOrderById(@RequestParam String orderId){
        Response<OrderEntity> result = orderService.findOrderById(orderId);
        HttpStatus httpStatus = HttpStatus.valueOf(result.getStatusCode());
        return ResponseEntity.status(httpStatus).body(result);
    }


    @PutMapping("/update")
    public ResponseEntity<Response<OrderEntity>> updateOrder(@RequestParam String orderId, @RequestParam String userId, @RequestBody UpdateOrderRequest request){
        Response<OrderEntity> result = orderService.update(orderId, userId, request);
        HttpStatus httpStatus = HttpStatus.valueOf(result.getStatusCode());
        return ResponseEntity.status(httpStatus).body(result);
    }

    //    @PutMapping("/update")
//    @CircuitBreaker(name = "Order-Service", fallbackMethod = "fallback")
//    @TimeLimiter(name = "Order-Service")
//    public CompletableFuture<ResponseEntity<Response<Object>>> updateOrder(@RequestParam String orderId, @RequestParam String userId, @RequestBody UpdateOrderRequest request, BindingResult errors) {
//        CompletableFuture<Object> service =
//                CompletableFuture.supplyAsync(() -> orderService.update(orderId, userId, request))
//                        .thenApplyAsync(updateEntity -> new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Update order successfully", updateEntity));
//
//        return service.thenApplyAsync(result -> {
//            Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Update order successfully", result);
//            return ResponseEntity.ok().body(response);
//        });
//    }

//    public CompletableFuture<ResponseEntity<Response<Object>>> updateOrder(@RequestParam String orderId, @RequestParam String userId, @RequestBody UpdateOrderRequest request, BindingResult errors, TimeoutException ex) {
//        // fetch results from the cache
//        Response<Object> response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE,ex.getMessage(),null)
//        return CompletableFuture.supplyAsync(() -> ResponseEntity.internalServerError().body(response));
//    }

}
