package com.coreJava.commerce.catalog;

import com.coreJava.commerce.common.InsufficientStockException;

import java.math.BigDecimal;

public class Product {
    private Long  id;
    private String name;

    private Category category;
    private int stockQuantity;

    private BigDecimal price;

    public Product(Long id, String name, Category category, int stockQuantity, BigDecimal price) {
        if (id <=0 || name.isEmpty() || stockQuantity<0 || price.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalArgumentException();
        }


        this.id = id;
        this.name = name;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void increaseStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void decreaseStock(int quantity){
        if (!hasEnoughStock(quantity)){
            throw new InsufficientStockException();
        }
        this.stockQuantity -= quantity;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void changePrice (BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public boolean hasEnoughStock(int stockQuantity){
        return this.stockQuantity>=stockQuantity;
    }
}
