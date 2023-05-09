package com.javagrind.productservice.service;

import com.javagrind.productservice.dto.request.CreateProductRequest;
import com.javagrind.productservice.dto.request.DeleteProductRequest;
import com.javagrind.productservice.dto.request.FindProductRequest;
import com.javagrind.productservice.dto.request.UpdateProductRequest;
import com.javagrind.productservice.entity.ProductEntity;

public interface ProductService {
    ProductEntity create(CreateProductRequest request);

    Object findProduct(FindProductRequest request);
    ProductEntity update(String id, UpdateProductRequest request);

    String delete(DeleteProductRequest request);

}
