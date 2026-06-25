package com.coreJava.commerce.cart;

import com.coreJava.commerce.catalog.Product;

import java.math.BigDecimal;

public class CartItem {
    private Product item;
    private int quantity;

    public CartItem(Product item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Product getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }


    public void increaseQuantity(int additionalQuantity) {
        this.quantity += additionalQuantity;
    }


    public void changeQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public BigDecimal getLineTotal() {
        BigDecimal quantityBigDecimal = BigDecimal.valueOf(quantity);
        return quantityBigDecimal.multiply(item.getPrice());
    }
}
