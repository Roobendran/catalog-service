package com.mms.catalogservice.service.impl;

import com.mms.catalogservice.dto.ProductDTO;
import com.mms.catalogservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<ProductDTO> findAllProducts() {
        return new ArrayList<>();
    }

    @Override
    public ProductDTO findById(Long id) {
        return null;
    }

    @Override
    public ProductDTO findByName(String name) {
        return null;
    }

    @Override
    public List<ProductDTO> findByCategory(String category) {
        return new ArrayList<>();
    }
}
