package com.javagrind.productservice.repositories;

import com.javagrind.productservice.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductEntity,String> {
    @Query("{ '_id' : ?0 }")
    Optional<ProductEntity> findById(String id);

    @Query("{ 'productName' : ?0 }")
    Optional<List<ProductEntity>> findByName(String name);

    @Query("{ $or: [ " +
            "{ 'productName': { $regex: ?0, $options: 'i' } }, " +
            "{ 'description': { $regex: ?0, $options: 'i' } } ] }")
    Optional <List<ProductEntity>> findSimilar(String match);

}
