package com.coreJava.commerce.catalog;

import com.coreJava.commerce.common.InsufficientStockException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ProductTest {
    @Test
    void decreaseStock_shouldDecreaseStock_whenQuantityIsValid() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));


        p1.decreaseStock(20);
        Assertions.assertEquals(30, p1.getStockQuantity());


    }

    @Test
    void decreaseStock_shouldThrowException_whenStockIsNotEnough() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

//        p1.decreaseStock(200);

        Assertions.assertThrows(InsufficientStockException.class, () -> p1.decreaseStock(500));

    }

    @Test
    void hasEnoughStock_shouldReturnTrue_whenRequestedQuantityIsLessThanStock() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

        Assertions.assertTrue(p1.hasEnoughStock(40));
    }

    @Test
    void hasEnoughStock_shouldReturnFalse_whenRequestedQuantityIsGreaterThanStock() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));

        Assertions.assertFalse(p1.hasEnoughStock(51));
    }



    @Test
    void changePrice_shouldChangePrice_whenRequestedPriceIsValid() {
        Category c1 = new Category(1L, "A");
        Product p1 = new Product(1L, "B", c1, 50, BigDecimal.valueOf(540));


        p1.changePrice(BigDecimal.valueOf(500));
        Assertions.assertEquals(BigDecimal.valueOf(500), p1.getPrice());
    }
}
