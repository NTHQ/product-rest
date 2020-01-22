package com.centric.productws.dao;

import com.centric.productws.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductDao implements IProductDao<Product> {
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<Product> getByField(String field, String value) {
        final String QUERY = "SELECT * FROM product WHERE " + field + " = ?";
        return jdbc.query(QUERY, new Object[]{value}, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Product insert(Product product) throws DataIntegrityViolationException {
        final String QUERY = "INSERT INTO product (id, name, description, brand, tags, category, created_at) VALUES (?,?,?,?,?,?,?);";
        String productId = UUID.randomUUID().toString();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String createdAt = ts.toString();

        int num = jdbc.update(QUERY, new Object[]{productId, product.getName(), product.getDescription(), product.getBrand(), String.join(",", product.getTags()), product.getCategory(), createdAt});

        if (num == 0) { // Insert was unsuccessful; return null
            return null;
        } else { // Find the product from the DB using the same ID used in INSERT
            List<Product> products = getByField("id", productId);
            return products.size() > 0 ? products.get(0) : null;
        }
    }
}
