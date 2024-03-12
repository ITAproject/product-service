package com.example.products_service.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "products")
public record Product (
        @Id
        String id,
        String name,
        String description,
        double price
) { }
