package com.javagrind.orderservice.serviceClient.ProductServiceClient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDao {

    private String id;

    private String productName;

    private Long price;

    private String description;

    private Long amounts;

    private Long like;
}
