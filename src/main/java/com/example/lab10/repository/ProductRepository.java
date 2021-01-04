package com.example.lab10.repository;

import com.example.lab10.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Product product) {
        String sql = "INSERT INTO products VALUES (NULL, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getAvailableStock());
    }

    public Optional<Product> getProductByName(String name) {
        String sql = "SELECT * FROM products WHERE name = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                    Product p = new Product();
                    p.setId(resultSet.getInt("id"));
                    p.setName(resultSet.getString("name"));
                    p.setPrice(resultSet.getDouble("price"));
                    p.setAvailableStock(resultSet.getInt("available_stock"));
                    return p;
                }
            }, name);
            return Optional.of(product);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Product> getProductById(Integer id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                    Product p = new Product();
                    p.setId(resultSet.getInt("id"));
                    p.setName(resultSet.getString("name"));
                    p.setPrice(resultSet.getDouble("price"));
                    p.setAvailableStock(resultSet.getInt("available_stock"));
                    return p;
                }
            }, id);
            return Optional.of(product);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void decrementStock(Product product, int quantity) {
        String sql = "UPDATE products set available_stock = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getAvailableStock() - quantity, product.getId());
    }

    public void incrementStock(Product product, int quantity) {
        String sql = "UPDATE products set available_stock = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getAvailableStock() + quantity, product.getId());
    }
}
