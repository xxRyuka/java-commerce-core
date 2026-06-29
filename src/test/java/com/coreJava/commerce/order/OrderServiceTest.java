package com.coreJava.commerce.order;

import com.coreJava.commerce.cart.Cart;
import com.coreJava.commerce.catalog.Category;
import com.coreJava.commerce.catalog.Product;
import com.coreJava.commerce.common.InsufficientStockException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OrderServiceTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void createOrder_shouldCreateOrderAndDecreaseProductStock_whenCartIsValid() {


        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

        Cart cart = new Cart(1L);
        cart.addProduct(p1, 20);

        OrderService service = new OrderService();

        Order order = service.createOrder(cart);

        Assertions.assertEquals(1L, order.getId()); // bu db işlemleri guidle vs calısırken gereksiz satir ama ekledim order olusmalı !
        Assertions.assertEquals(30, p1.getStockQuantity());
        Assertions.assertTrue(cart.isEmpty()); // cart temizlenmeli

    }

    @Test
    void createOrder_shouldThrowException_whenCartIsEmpty() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

        Cart cart = new Cart(1L);

        OrderService service = new OrderService();


        Assertions.assertThrows(IllegalStateException.class, () -> service.createOrder(cart));
    }

    // siparis olusturulurken yetersiz stock hatası testi
    @Test
    void createOrder_shouldThrowException_whenStockIsInvalid() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

        Cart cart = new Cart(1L);


//        CartItem cartItem = new CartItem(p1,150);

        cart.addProduct(p1, 1150);

        OrderService service = new OrderService();

        Assertions.assertThrows(InsufficientStockException.class, () -> service.createOrder(cart));


    }


    //findOrderById sipariş varsa Optional dolu
    @Test
    void findOrderById_shouldReturnTrue_whenOptionalNotEmpty() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

        Cart cart = new Cart(1L);


//        CartItem cartItem = new CartItem(p1,150);

        cart.addProduct(p1, 1);

        OrderService service = new OrderService();

        Order order = service.createOrder(cart);

        var OrderMaybe = service.findOrderById(1L);

        Assertions.assertTrue(service.findOrderById(1L).isPresent());
        Assertions.assertEquals(1L,OrderMaybe.get().getId());

    }

    @Test
    void findOrderById_shouldReturnFalse_whenOptionalIsEmpty() {

        OrderService service = new OrderService();

        Assertions.assertTrue(service.findOrderById(1L).isEmpty());

    }
}
