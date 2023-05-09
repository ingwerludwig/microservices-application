package com.javagrind.orderservice.controller;

import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.CreateOrderRequest;
import com.javagrind.orderservice.dto.request.FindOrderRequest;
import com.javagrind.orderservice.dto.request.UpdateOrderRequest;
import com.javagrind.orderservice.entity.OrderEntity;
import com.javagrind.orderservice.handler.BadRequestExceptionHandler;
import com.javagrind.orderservice.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class.getName());
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Response<OrderEntity>> createProduct (@Valid @RequestBody CreateOrderRequest request, BindingResult errors){
        Response<OrderEntity> response;

        if (errors.hasErrors()) BadRequestExceptionHandler.handle(errors);

        try {
            OrderEntity newProduct = orderService.create(request);
            response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Create order successfully", newProduct);
            return ResponseEntity.ok().body(response);
        } catch (HttpClientErrorException.Unauthorized ex) {
            response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
            System.err.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (HttpClientErrorException.Forbidden ex) {
            response = new Response<>(HttpStatus.FORBIDDEN.value(), Boolean.FALSE, ex.getMessage(), null);
            System.err.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception ex) {
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, ex.getMessage(), null);
            System.err.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getOrder")
    public ResponseEntity<Response<Object>> findProduct (@Valid @RequestBody FindOrderRequest request, BindingResult errors){
        Response<Object> response;

        if (errors.hasErrors()) {return BadRequestExceptionHandler.handle(errors);
        } else {

            try {
                Object result = orderService.findOrder(request);
                response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Find order successfully", result);
                return ResponseEntity.ok().body(response);
            } catch (HttpClientErrorException.Unauthorized ex) {
                response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            } catch (HttpClientErrorException.Forbidden ex) {
                response = new Response<>(HttpStatus.FORBIDDEN.value(), Boolean.FALSE, ex.getMessage(), null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            } catch (Exception ex) {
                response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, ex.getMessage(), null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Response<OrderEntity>> updateProduct(@RequestParam String orderId, @RequestParam String userId, @RequestBody UpdateOrderRequest request, BindingResult errors){
        Response<OrderEntity> response;

        if (errors.hasErrors()) BadRequestExceptionHandler.handle(errors);

        try {
            OrderEntity updatedProduct = orderService.update(orderId, userId, request);
            response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Update order successfully", updatedProduct);
            return ResponseEntity.ok().body(response);
        } catch (HttpClientErrorException.Unauthorized ex) {
            response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
            System.err.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (HttpClientErrorException.Forbidden ex) {
            response = new Response<>(HttpStatus.FORBIDDEN.value(), Boolean.FALSE, ex.getMessage(), null);
            System.err.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception ex) {
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, ex.getMessage(), null);
            System.err.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
