package com.example.lab10.exception;

public class NoStockAvailableException extends RuntimeException {
    public NoStockAvailableException() {
        super("No stock available!");
    }
}
