package com.javagrind.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(
        name = "orders",
        indexes = @Index(name = "index_id_order", columnList = "order_id")
)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", updatable = false)
    private String id;

    @Column(name = "product_id", updatable = false)
    private String productId;

    @Column(name = "user_id", updatable = false)
    private String user_id;

    @Column(name = "product_name", updatable = false)
    private String productName;

    @Column(name = "description", updatable = false)
    private String description;

    @Column(name = "amounts", updatable = false)
    private Long amounts;

    @Column(name = "price")
    private Long price;

    @Enumerated(EnumType.STRING)
    private OrderPayment paymentStatus;

    private LocalDateTime expiredOn;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public OrderEntity(String productId, String userId, String productName, String description, Long amounts, Long price) {
        this.productId = productId;
        this.user_id = userId;
        this.productName = productName;
        this.description = description;
        this.amounts = amounts;
        this.price = price;
    }


    @PrePersist
    private void setDefaultExpiredOn() {
        if (expiredOn == null) expiredOn = LocalDateTime.now().plusHours(6);
        if (paymentStatus == null) paymentStatus = OrderPayment.PAYMENT_WAITING;
    }

}
