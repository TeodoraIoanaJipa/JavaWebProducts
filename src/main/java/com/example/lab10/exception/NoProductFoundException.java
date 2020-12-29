package com.example.lab10.exception;

public class NoProductFoundException extends RuntimeException {
    public NoProductFoundException() {
        super("No product was found !");
    }
}
