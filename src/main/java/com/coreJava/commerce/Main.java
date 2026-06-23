package com.coreJava.commerce;

import com.coreJava.commerce.catalog.Category;
import com.coreJava.commerce.catalog.Product;
import com.coreJava.commerce.common.InsufficientStockException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Category elektronik = new Category(1L, "elektrik");
        Category book = new Category(2L, "kitap");

        Product keyboard1 = new Product(1L, "wraith", elektronik, 18, new BigDecimal("180.0"));
        Product laptop1 = new Product(3L, "hp omen", elektronik, 21, new BigDecimal("1800.0"));
        Product book1 = new Product(2L, "mein kopft", book, 120, new BigDecimal("5.00"));
        Product testPr1 = new Product(4L, "test", book, 50, new BigDecimal("50"));

        List<Product> productList = new ArrayList<>();
        productList.add(keyboard1);
        productList.add(laptop1);
        productList.add(book1);
        productList.add(testPr1);


        book1.increaseStock(5);
        laptop1.decreaseStock(3);

        keyboard1.changePrice(new BigDecimal("175.0"));

        testPr1.changeCategory(elektronik);
        testPr1.changeName("changedTEST");
        try {
            testPr1.decreaseStock(60);
        } catch (InsufficientStockException ex) {
            System.out.println("yetersiz stock hata msg : " + ex.getMessage());
        }

        for (Product product : productList) {
            System.out.printf(
                    "id : %d \n" +
                            "name : %s \n" +
                            "category : %s\n" +
                            "stock : %d \n" +
                            "price : %f \n\n"

                    , product.getId(), product.getName(), product.getCategory().getName(), product.getStockQuantity(), product.getPrice());

        }



    }
}
