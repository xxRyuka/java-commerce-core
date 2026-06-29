package com.coreJava.commerce.cart;

import com.coreJava.commerce.catalog.Category;
import com.coreJava.commerce.catalog.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CartTest {

    @Test
    void addProduct_shouldAddNewCartItem_whenProductDoesNotExistInCart() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

        Cart cart = new Cart(1L);

        cart.addProduct(p1, 2);

        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertEquals(2, cart.getTotalQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(540 * 2), cart.getTotalAmount());

    }


    @Test
    void addProduct_shouldIncreaseQuantity_whenSameProductAddedAgain() {

        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

        Cart cart = new Cart(1L);

        cart.addProduct(p1, 2);
        cart.addProduct(p1, 2);


        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertEquals(4, cart.getTotalQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(540 * 4), cart.getTotalAmount());

    }


    @Test
    void changeProductQuantity_shouldChangeQuantity_whenProductExists() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

        Cart cart = new Cart(1L);

        cart.addProduct(p1, 2);

        boolean result = cart.changeProductQuantity(1L, 50);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertEquals(50, cart.getTotalQuantity());

    }
}
