package com.javagrind.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "order_id",columnDefinition = "UUID", updatable = false)
    private UUID id;

    @Column(name = "product_id", updatable = false)
    private String productId;

    @Column(name = "user_id", updatable = false)
    private UUID user_id;

    @Column(name = "product_name", updatable = false)
    private String productName;

    @Column(name = "description", updatable = false)
    private String description;

    @Column(name = "amounts")
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

    public OrderEntity(String productId, UUID userId, String productName, String description, Long amounts, Long price) {
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
