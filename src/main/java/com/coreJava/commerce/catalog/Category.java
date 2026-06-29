package com.coreJava.commerce.catalog;

import java.util.Objects;

public class Category {
    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void rename(String name) {

        if (name.isEmpty()) { // isBlanktan farkı ne ?
            throw new IllegalArgumentException();

        }
        this.name = name;
    }

// toString , equals , hasCode



    // Domain Rule
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Category category)) {
            return false; // obj ile nesne'nin sinifi ayni değilse direk false
        }
// int digeri string product.sku , this.sku
        return Objects.equals(category.id, this.id);
    }

    @Override
    public String toString() {
        return this.name + " " + this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    public Category(Long id, String name) {

        if (id <= 0) {
            throw new IllegalArgumentException();

        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException();

        }
        this.id = id;
        this.name = name;

    }

    public Long getId() {
        return id;
    }
}
