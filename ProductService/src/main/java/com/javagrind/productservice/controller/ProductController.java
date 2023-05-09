package com.javagrind.productservice.controller;

import com.javagrind.productservice.dto.Response;
import com.javagrind.productservice.dto.request.CreateProductRequest;
import com.javagrind.productservice.dto.request.DeleteProductRequest;
import com.javagrind.productservice.dto.request.FindProductRequest;
import com.javagrind.productservice.dto.request.UpdateProductRequest;
import com.javagrind.productservice.entity.ProductEntity;
import com.javagrind.productservice.handler.BadRequestExceptionHandler;
import com.javagrind.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Response<ProductEntity>> createProduct (@Valid @RequestBody CreateProductRequest request, BindingResult errors){
        Response<ProductEntity> response;

        if (errors.hasErrors()) BadRequestExceptionHandler.handle(errors);

        try {
            ProductEntity newProduct = productService.create(request);
            response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Create product successfully", newProduct);
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

    @GetMapping("/getProduct")
    public ResponseEntity<Response<Object>> findProduct (@Valid @RequestBody FindProductRequest request, BindingResult errors){
        Response<Object> response;

        if (errors.hasErrors()) {return BadRequestExceptionHandler.handle(errors);
        } else {

            try {
                Object result = productService.findProduct(request);
                response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Find product successfully", result);
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
    public ResponseEntity<Response<ProductEntity>> updateProduct(@RequestParam String id, @RequestBody UpdateProductRequest request, BindingResult errors){
        Response<ProductEntity> response;

        if (errors.hasErrors()) BadRequestExceptionHandler.handle(errors);

        try {
            ProductEntity updatedProduct = productService.update(id, request);
            response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Update product successfully", updatedProduct);
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

    @PutMapping("/delete")
    public ResponseEntity<Response<String>> deleteProduct(@Valid @RequestBody DeleteProductRequest request, Errors errors){
        Response<String> response;

        if (errors.hasErrors()) BadRequestExceptionHandler.handle(errors);

        try {
            String result = productService.delete(request);
            response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Delete product successfully", result);
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
