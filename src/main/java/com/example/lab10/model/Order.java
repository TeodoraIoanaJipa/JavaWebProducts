package com.example.lab10.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Order {

    private int id;
    private double totalPrice;
    private Status status;
    private List<Product> products;
}
