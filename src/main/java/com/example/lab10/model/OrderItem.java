package com.example.lab10.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItem {

    private int id;
    private Order order;
    private Product product;
    private int quantity;
}
