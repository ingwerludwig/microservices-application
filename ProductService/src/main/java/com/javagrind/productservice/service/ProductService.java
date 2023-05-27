package com.javagrind.productservice.service;

import com.javagrind.productservice.dto.request.*;
import com.javagrind.productservice.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductEntity create(CreateProductRequest request);

    List<ProductEntity> findProduct(FindProductRequest request);
    ProductEntity findProductById(FindProductByIdRequest request);
    ProductEntity update(String id, UpdateProductRequest request);

    String delete(DeleteProductRequest request);

}
