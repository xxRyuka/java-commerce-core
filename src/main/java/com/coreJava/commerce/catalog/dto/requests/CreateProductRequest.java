package com.coreJava.commerce.catalog.dto.requests;

import java.math.BigDecimal;

public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private int stockQuantity;

    private Long categoryId;

    public CreateProductRequest(String name, BigDecimal price, int stockQuantity, Long categoryId) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
