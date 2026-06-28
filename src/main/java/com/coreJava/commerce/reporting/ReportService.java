package com.coreJava.commerce.reporting;

import com.coreJava.commerce.catalog.Product;

import java.math.BigDecimal;
import java.util.List;

public class ReportService {

    private final List<Product> products;

    public ReportService(List<Product> products) {
        this.products = products;
    }

    public int getTotalStockQuantity() {
        return products.stream().mapToInt(Product::getStockQuantity).sum();
    }

    public BigDecimal getTotalInventoryValue() {
        return products.stream().map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getStockQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public double getAverageProductPrice() {
        return products.stream().mapToDouble(product -> product.getPrice().doubleValue()).average().orElse(0);
    }

}
