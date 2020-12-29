package com.example.lab10.dto;

import com.example.lab10.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private int id;
    private double totalPrice;
    private List<ItemDto> products;
    private Status status;
}
