package com.example.lab10.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {

    private int id;
    private String name;
    private double price;
    private int availableStock;
}
