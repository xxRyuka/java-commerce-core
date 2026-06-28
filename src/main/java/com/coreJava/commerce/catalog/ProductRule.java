package com.coreJava.commerce.catalog;

@FunctionalInterface
public interface ProductRule {
    boolean isSatisfiedBy(Product product);
}
