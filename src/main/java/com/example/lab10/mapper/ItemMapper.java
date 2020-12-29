package com.example.lab10.mapper;

import com.example.lab10.dto.ItemDto;
import com.example.lab10.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemDto toDto(OrderItem itemOrdered) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(itemOrdered.getProduct().getName());
        itemDto.setPricePerItem(itemOrdered.getProduct().getPrice());
        itemDto.setQuantityOrdered(itemOrdered.getQuantity());

        return itemDto;
    }
}
