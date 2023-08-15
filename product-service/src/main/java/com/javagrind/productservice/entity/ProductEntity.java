package com.javagrind.productservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class ProductEntity {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    private String productName;

    private Long price;

    private String description;

    private Long amounts;

    @Field()
    private Long like=0L;

    public ProductEntity(String productName, Long price, String description, Long amounts) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.amounts = amounts;
    }
}
