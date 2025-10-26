package com.mms.catalogservice.service.impl;

import com.mms.catalogservice.model.Product;
import com.mms.catalogservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

//    @Autowired
//    ProductRepository productRepository;
//
//    @Autowired
//    ProductEntityMapper productEntityMapper;

    @Override
    public Product createProduct(Product product) {
//        productRepository.save(productEntityMapper.map(product));
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
//        Product productDb = productRepository.findById(product.getId());
//        productRepository.save(productEntityMapper.map(product));
        return product;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    @Override
    public boolean deleteProduct(String name) {
        return false;
    }

    @Override
    public List<Product> findAllProducts() {
        return null;
    }

    @Override
    public Product findProductById(Long id) {
        return null;
    }

    @Override
    public Product findProductByName(String name) {
        return null;
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }
}
