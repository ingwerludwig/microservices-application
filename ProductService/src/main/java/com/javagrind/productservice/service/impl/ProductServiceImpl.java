package com.javagrind.productservice.service.impl;

import com.javagrind.productservice.dao.ProductDao;
import com.javagrind.productservice.dto.request.CreateProductRequest;
import com.javagrind.productservice.dto.request.DeleteProductRequest;
import com.javagrind.productservice.dto.request.FindProductRequest;
import com.javagrind.productservice.dto.request.UpdateProductRequest;
import com.javagrind.productservice.entity.ProductEntity;
import com.javagrind.productservice.repositories.ProductRepository;
import com.javagrind.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDao productDao;

    @Override
    @Transactional
    public ProductEntity create(CreateProductRequest request) {
        Optional<List<ProductEntity>> existProduct = productRepository.findByName(request.getProductName());

        if (!existProduct.get().isEmpty()) {
            System.err.println("Product name has been taken");
            throw new IllegalArgumentException("Product name has been taken");
        }else{
            ProductEntity newProduct = new ProductEntity(request.getProductName(),request.getPrice(),request.getDescription(),request.getAmounts());
            productRepository.save(newProduct);
            return newProduct;
        }
    }

    @Override
    public Object findProduct(FindProductRequest request) {
        Optional<List<ProductEntity>> result = productRepository.findSimilar(request.getProductName());

        if (result.map(products -> !products.isEmpty()).orElse(null))  return result;
        else return null;
    }

    @Override
    @Transactional
    public ProductEntity update(String id, UpdateProductRequest request) {
        Optional<ProductEntity> requestedProduct = productRepository.findById(id);

        if (requestedProduct.isPresent() && requestedProduct.get().getId().equals(id)){
            Long updated = productDao.updateById(id,request);
            if(updated == 1L) {
                Optional<ProductEntity> updatedProduct = productRepository.findById(id);
                return updatedProduct.filter(product -> product.getId().equals(id)).orElse(null);
            }else{
                throw new NoSuchElementException("Failed to update");
            }
        }else{ throw new NoSuchElementException("Product not found");}
    }

    @Override
    @Transactional
    public String delete(DeleteProductRequest request) {
        Optional<ProductEntity> deletedProduct = productRepository.findById(request.getProductId());

        if (deletedProduct.isPresent() && deletedProduct.get().getId().equals(request.getProductId())){
            productDao.deleteById(request.getProductId());
            return deletedProduct.get().getProductName() + " with ID : " + deletedProduct.get().getId() +" deleted ";
        }
        else{ throw new NoSuchElementException("Product not found");}
    }
}
