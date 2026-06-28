package com.coreJava.commerce.common;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException() {
        super("insufficient stock quantity");
    }

    public InsufficientStockException(String customMsg) {
        super(customMsg);
    }

    public InsufficientStockException(String customMsg, Throwable cause) {
        super(customMsg, cause);
    }
}
