package com.javagrind.orderservice.controller;

import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.FindOrderRequest;
import com.javagrind.orderservice.dto.request.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;
import com.javagrind.orderservice.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class.getName());
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Response<OrderEntity>> createOrder(@Valid @RequestBody CreateOrderRequest request) throws Exception {
        OrderEntity result = orderService.create(request).get();
        Response<OrderEntity> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Create Order Successfully", result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/getOrder")
    public ResponseEntity<Response<List<OrderEntity>>> findOrder(@Valid @RequestBody FindOrderRequest request) throws ExecutionException, InterruptedException {
        List<OrderEntity> result = orderService.findOrderByUserId(request).get();
        Response<List<OrderEntity>> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Find Order Successfully", result);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<Response<OrderEntity>> findOrderById(@RequestParam String orderId) throws ExecutionException, InterruptedException {
        OrderEntity result = orderService.findOrderById(orderId).get();
        Response<OrderEntity> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Find Order Successfully", result);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Response<OrderEntity>> updateOrder(@RequestParam String orderId, @RequestParam String userId, @RequestBody UpdateOrderRequest request) throws ExecutionException, InterruptedException {
        OrderEntity result = orderService.update(orderId, userId, request).get();
        Response<OrderEntity> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Update Order Successfully", result);
        return ResponseEntity.ok().body(response);
    }

}
