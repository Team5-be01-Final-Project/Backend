package com.sales.BPS.mexception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String message) {
        super(message);
    }
}
