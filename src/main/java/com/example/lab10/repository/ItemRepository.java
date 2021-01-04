package com.example.lab10.repository;

import com.example.lab10.exception.NoProductFoundException;
import com.example.lab10.model.Order;
import com.example.lab10.model.OrderItem;
import com.example.lab10.model.Product;
import com.example.lab10.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ItemRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductRepository productRepository;

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


    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT * FROM order_items WHERE orderid = " + orderId;
        List<OrderItem> orderItemList = new ArrayList<>();
        try {
            List<Map<String, Object>> orderItems = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> map : orderItems) {
                OrderItem item = new OrderItem();
                item.setId((Integer) map.get("id"));
                Optional<Product> product = productRepository.getProductById((Integer) map.get("productid"));
                if(product.isPresent()){
                    item.setProduct(product.get());
                }else{
                    throw new NoProductFoundException();
                }
                item.setQuantity((Integer) map.get("quantity"));
                orderItemList.add(item);
            }
            return orderItemList;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
