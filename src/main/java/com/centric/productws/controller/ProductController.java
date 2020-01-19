package com.centric.productws.controller;

import com.centric.productws.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/findProductByCategory")
    public List<Product> findProductByCategory(@RequestParam(value = "category") String category) {
        final String QUERY = "SELECT * FROM product WHERE category = ?";
        List<Product> product;
        try {
            product = jdbc.query(QUERY, new Object[]{category}, new BeanPropertyRowMapper<>(Product.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // No product found; return a null value
        }

        return product;
    }
}
