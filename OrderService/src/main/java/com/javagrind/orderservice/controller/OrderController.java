package com.javagrind.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.Order.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.Order.FindOrderRequest;
import com.javagrind.orderservice.dto.request.Order.UpdateOrderRequest;
import com.javagrind.orderservice.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class.getName());
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Response<Object>> createProduct (@Valid @RequestBody CreateOrderRequest request, BindingResult errors) throws JsonProcessingException {
        Object newProduct = orderService.create(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Create order successfully", newProduct);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getOrder")
    public ResponseEntity<Response<Object>> findProduct (@Valid @RequestBody FindOrderRequest request, BindingResult errors){
        Object result = orderService.findOrder(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Find order successfully", result);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Response<Object>> updateProduct(@RequestParam String orderId, @RequestParam String userId, @RequestBody UpdateOrderRequest request, BindingResult errors){
        Object updatedProduct = orderService.update(orderId, userId, request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Update order successfully", updatedProduct);
        return ResponseEntity.ok().body(response);
    }

}
