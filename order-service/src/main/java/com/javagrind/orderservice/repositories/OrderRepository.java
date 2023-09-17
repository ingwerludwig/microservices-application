package com.javagrind.orderservice.repositories;

import com.javagrind.orderservice.entity.OrderEntity;
import com.javagrind.orderservice.entity.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @Query("SELECT order FROM OrderEntity order WHERE order.id = :id")
    Optional<OrderEntity> findById(UUID id);

    @Query("SELECT order FROM OrderEntity order WHERE order.user_id = :userId")
    Optional<List<OrderEntity>> findByUserId(String userId);

}
