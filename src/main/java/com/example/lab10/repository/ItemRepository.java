package com.example.lab10.repository;

import com.example.lab10.model.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public ItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(OrderItem item, int orderId) {
        String sql = "INSERT INTO order_items VALUES(NULL, ?, ?, ?)";

        jdbcTemplate.update(sql,
                item.getProduct().getId(),
                orderId,
                item.getQuantity());
    }
}
