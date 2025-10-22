package com.mms.catalogservice.service;

import com.mms.catalogservice.dto.ProductDTO;

import java.util.List;

public interface ProductService {

   List<ProductDTO> findAllProducts();
    ProductDTO findById(String id);
    ProductDTO findByName(String name);
    List<ProductDTO> findByCategory(String category);
}
