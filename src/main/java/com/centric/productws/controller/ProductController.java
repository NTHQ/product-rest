package com.centric.productws.controller;

import com.centric.productws.dao.ProductDao;
import com.centric.productws.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductDao productDao;

    @PostMapping("/createProduct")
    public Product createProduct(String name, String description, String brand, String tags, String category) {
        return productDao.insert(name, description, brand, tags, category);
    }

    @GetMapping("/findProductByCategory")
    public List<Product> findProductByCategory(@RequestParam(value = "category") String category) {
        return productDao.getByField("category", category);
    }
}
