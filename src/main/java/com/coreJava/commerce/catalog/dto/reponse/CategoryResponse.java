package com.coreJava.commerce.catalog.dto.reponse;

import java.math.BigDecimal;

public class CategoryResponse {
    private String name;
    private BigDecimal price;
    private int stockQuantity;

    private String categoryName;

    public CategoryResponse(String name, BigDecimal price, int stockQuantity, String  categoryName) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryName = categoryName;
    }

    public String getcategoryName() {
        return categoryName;
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
