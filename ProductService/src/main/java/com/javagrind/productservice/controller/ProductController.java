package com.javagrind.productservice.controller;

import com.javagrind.productservice.dto.Response;
import com.javagrind.productservice.dto.request.CreateProductRequest;
import com.javagrind.productservice.dto.request.DeleteProductRequest;
import com.javagrind.productservice.dto.request.FindProductRequest;
import com.javagrind.productservice.dto.request.UpdateProductRequest;
import com.javagrind.productservice.entity.ProductEntity;
import com.javagrind.productservice.handler.GlobalExceptionHandler;
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
    public ResponseEntity<Response<Object>> createProduct (@Valid @RequestBody CreateProductRequest request, BindingResult errors){
        ProductEntity newProduct = productService.create(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Create product successfully", newProduct);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getProduct")
    public ResponseEntity<Response<Object>> findProduct (@Valid @RequestBody FindProductRequest request, BindingResult errors){
        Object result = productService.findProduct(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Find product successfully", result);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Response<Object>> updateProduct(@RequestParam String id, @RequestBody UpdateProductRequest request, BindingResult errors){
        ProductEntity updatedProduct = productService.update(id, request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Update product successfully", updatedProduct);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/delete")
    public ResponseEntity<Response<Object>> deleteProduct(@Valid @RequestBody DeleteProductRequest request, Errors errors){
        String result = productService.delete(request);
        Response<Object> response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Delete product successfully", result);
        return ResponseEntity.ok().body(response);
    }

}
