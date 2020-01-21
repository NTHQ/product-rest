package com.centric.productws.dao;

import com.centric.productws.model.Product;

import java.util.List;

public interface IProductDao<T> {
    List<T> getByField(String field, String value);

    Product insert(String name, String description, String brand, String tags, String category);
}
