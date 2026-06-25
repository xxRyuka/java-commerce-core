package com.coreJava.commerce.order;

import com.coreJava.commerce.catalog.Product;

import java.math.BigDecimal;

public class OrderItem {
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;

    public OrderItem(Product pr, int quantity) {
        this.productId = pr.getId();
        this.productName = pr.getName();
        this.quantity = quantity;
        this.unitPrice = pr.getPrice();
    }


    public BigDecimal getLineTotal() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        totalAmount = unitPrice.multiply(BigDecimal.valueOf(quantity));
        return totalAmount;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
