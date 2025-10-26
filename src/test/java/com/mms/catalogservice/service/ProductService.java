package com.mms.catalogservice.service;

import com.mms.catalogservice.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Product product);
    boolean deleteProduct(Long id);
    boolean deleteProduct(String name);
    List<Product> findAllProducts();
    Product findProductById(Long id);
    Product findProductByName(String name);
    List<Product> findProductByCategory(String category);
}
