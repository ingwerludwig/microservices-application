package com.javagrind.paymentservice.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDao {
    private String id;
    private String name;
    private Integer price;
    private Integer quantity;
    private String brand="OrangTua";
    private String category="Food";
    private String merchant_name="PT Borma";
}
