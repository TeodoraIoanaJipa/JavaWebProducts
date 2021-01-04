package com.example.lab10.controller;

import com.example.lab10.exception.NoOrderFoundException;
import com.example.lab10.exception.NoProductFoundException;
import com.example.lab10.exception.NoStockAvailableException;
import com.example.lab10.model.OrderItem;
import com.example.lab10.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody List<OrderItem> items) {
        try {
            return ResponseEntity.ok(orderService.save(items));
        } catch (NoProductFoundException | NoStockAvailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity cancelOrder(@RequestParam Integer orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("success");
        } catch (NoOrderFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
