package com.javagrind.productservice.dao;

import com.javagrind.productservice.dto.request.UpdateProductRequest;
import com.javagrind.productservice.entity.ProductEntity;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDao {
    private final MongoTemplate mongoTemplate;

    public Long updateById(String id, UpdateProductRequest request) {
        Update update = new Update().set("productName", request.getProductName())
                .set("price", request.getPrice())
                .set("description", request.getDescription())
                .set("amounts", request.getAmounts());

        UpdateResult result = mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(id)),
                update,
                ProductEntity.class
        );

        if (result.getModifiedCount() > 0 || result.getMatchedCount() > 0) {
            return 1L;
        } else {
            return 0L;
        }
    }


    public void deleteById(String id) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(id)),
                new Update().set("amounts", 0),
                ProductEntity.class
        );
    }
}
