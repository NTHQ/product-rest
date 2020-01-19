package com.centric.productws.controller;

import com.centric.productws.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    private JdbcTemplate jdbc;

    @PostMapping("/createProduct")
    public Product createProduct(String name, String description, String brand, String tags, String category) {
        final String QUERY = "INSERT INTO product (id, name, description, brand, tags, category, created_at) VALUES (?,?,?,?,?,?,?);";
        String productId = UUID.randomUUID().toString();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String createdAt = ts.toString();

        try {
            int num = jdbc.update(QUERY, new Object[]{productId, name, description, brand, tags, category, createdAt});

            if (num == 0) { // Insert was unsuccessful; return null
                return null;
            } else { // Find the product from the DB using the same ID used in INSERT
                return findProductById(productId);
            }
        } catch (DataAccessException e) {
            return null;
        }
    }

    private Product findProductById(@RequestParam(value = "id") String id) {
        final String QUERY = "SELECT * FROM product WHERE id = ?";
        Product product;
        try {
            product = jdbc.queryForObject(QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Product.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // No product found; return a null value
        }

        return product;
    }

    @GetMapping("/findProductByCategory")
    public List<Product> findProductByCategory(@RequestParam(value = "category") String category) {
        final String QUERY = "SELECT * FROM product WHERE category = ?";
        List<Product> products;
        try {
            products = jdbc.query(QUERY, new Object[]{category}, new BeanPropertyRowMapper<>(Product.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // No product found; return a null value
        }

        return products;
    }
}
