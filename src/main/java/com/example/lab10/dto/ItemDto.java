package com.example.lab10.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private String name;
    private double pricePerItem;
    private int quantityOrdered;
}
