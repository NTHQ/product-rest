package com.centric.productws.controller;

import com.centric.productws.dao.ProductDao;
import com.centric.productws.error.MissingProductFieldException;
import com.centric.productws.error.NoProductFoundException;
import com.centric.productws.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductDao productDao;

    @PostMapping("/createProduct")
    public Product createProduct(@RequestBody Product product) throws MissingProductFieldException {
        try {
            return productDao.insert(product);
        } catch (DataIntegrityViolationException e) {
            String msg = e.getCause().getMessage();
            throw new MissingProductFieldException(msg.substring(0, msg.indexOf(';')));
        }
    }

    @GetMapping("/findProductByCategory")
    public List<Product> findProductByCategory(@RequestParam(value = "category") String category) throws NoProductFoundException {
        List<Product> products = productDao.getByField("category", category);
        if (products == null || products.size() == 0) {
            throw new NoProductFoundException();
        }
        return products;
    }
}
