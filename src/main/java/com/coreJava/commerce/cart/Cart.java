package com.coreJava.commerce.cart;

import com.coreJava.commerce.catalog.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    Long id; //wrapper kullanıyoruz cunku null olabilir
    List<CartItem> items = new ArrayList<>();

    public Cart(Long id) {
        this.id = id;
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            return;
        }

        // item mevcutsa quantityi arttir
        for (CartItem item : items) {
            if (Objects.equals(item.getItem().getId(), product.getId())) {
                item.increaseQuantity(quantity);
                return;
            }
        }
        // mevcut değilse siparis satırı olustur
        CartItem cartItem = new CartItem(product, quantity);
        items.add(cartItem);
    }


    public boolean removeProduct(Long id) {

        return items.removeIf(cartItem -> cartItem.getItem().getId().equals(id));
    }

    public boolean changeProductQuantity(Long productId, int quantity) {
        for (CartItem item : items) {
            if (item.getItem().getId().equals(productId)) {
                item.changeQuantity(quantity);
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem price : items) {

            totalAmount = totalAmount.add(price.getLineTotal());
        }
        return totalAmount;
    }


    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (CartItem item : items) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }

}
