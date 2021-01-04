package com.example.lab10.repository;

import com.example.lab10.model.Order;
import com.example.lab10.model.Product;
import com.example.lab10.model.Status;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSql = "INSERT INTO orders VALUES(NULL, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setDouble(1, order.getTotalPrice());
            ps.setString(2, order.getStatus().toString());
            return ps;
        }, keyHolder);

        return (long) keyHolder.getKey().longValue();

        //jdbcTemplate.update(insertSql, order.getTotalPrice(), order.getStatus().toString());
    }

    public void updateOrderStatus(Order order){
        String sql = "UPDATE orders set status = ? WHERE id = ?";
        jdbcTemplate.update(sql, order.getStatus().name(), order.getId());
    }

    public Optional<Order> getOrderById(Integer id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try {
            Order order = jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
                Order newOrder = new Order();
                newOrder.setId(resultSet.getInt("id"));
                if(resultSet.getString("status").toUpperCase().equals(Status.ACTIVE.name())){
                    newOrder.setStatus(Status.ACTIVE);
                }else{
                    newOrder.setStatus(Status.CANCELLED);
                }
                newOrder.setTotalPrice(resultSet.getDouble("total_price"));
                return newOrder;
            }, id);
            return Optional.of(order);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
