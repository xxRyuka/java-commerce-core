package com.coreJava.commerce;

import com.coreJava.commerce.catalog.CatalogService;
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


        CatalogService catalogService = new CatalogService();

        catalogService.addProduct(keyboard1);
        catalogService.addProduct(laptop1);
        catalogService.addProduct(book1);
        catalogService.addProduct(testPr1);


        book1.increaseStock(5);
        laptop1.decreaseStock(3);

        keyboard1.changePrice(new BigDecimal("175.0"));

        testPr1.changeCategory(elektronik);
        testPr1.changeName("changedTEST");
        try {
            testPr1.decreaseStock(60);
        } catch (InsufficientStockException ex) {
            System.out.println("yetersiz stock hata msg : " + ex.getMessage() + "\n");
        }

        for (Product product : catalogService.getProducts()) {
            System.out.printf(
                    "id : %d \n" +
                            "name : %s \n" +
                            "category : %s\n" +
                            "stock : %d \n" +
                            "price : %f \n\n"

                    , product.getId(), product.getName(), product.getCategory().getName(), product.getStockQuantity(), product.getPrice());

        }

        Product foundProduct = catalogService.findById(2L);


        if (foundProduct != null) {
            System.out.println("Bulunan ürün: " + foundProduct.getName());
        } else {
            System.out.println("Ürün bulunamadı.");
        }

        for (Product filtered : catalogService.findProductsByCategoryId(2L)) {
            System.out.printf(
                    "id : %d \n" +
                            "name : %s \n" +
                            "category : %s\n" +
                            "stock : %d \n" +
                            "price : %f \n\n"

                    , filtered.getId(), filtered.getName(), filtered.getCategory().getName(), filtered.getStockQuantity(), filtered.getPrice());

        }

        for (Product filtered : catalogService.findProductsByMinPrice(new BigDecimal("20.0"))) {
            System.out.printf(
                    "id : %d \n" +
                            "name : %s \n" +
                            "category : %s\n" +
                            "stock : %d \n" +
                            "price : %f \n\n"

                    , filtered.getId(), filtered.getName(), filtered.getCategory().getName(), filtered.getStockQuantity(), filtered.getPrice());

        }

        catalogService.removeProductById(4L);
        for (Product product : catalogService.getProducts()) {
            System.out.printf(
                    "id : %d \n" +
                            "name : %s \n" +
                            "category : %s\n" +
                            "stock : %d \n" +
                            "price : %f \n\n"

                    , product.getId(), product.getName(), product.getCategory().getName(), product.getStockQuantity(), product.getPrice());

        }
        catalogService.addProduct(new Product(1L, "deneme", book, 50, new BigDecimal("20.00")));

    }
}
