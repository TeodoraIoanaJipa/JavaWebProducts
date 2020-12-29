package com.example.lab10.exception;

public class NoOrderFoundException extends RuntimeException{
    public NoOrderFoundException() {
        super("No order found.");
    }
}
