package com.mms.catalogservice.service.impl;

import com.mms.catalogservice.entity.ProductEntity;
import com.mms.catalogservice.entity.mappers.ProductEntityMapper;
import com.mms.catalogservice.model.Product;
import com.mms.catalogservice.model.mappers.ProductMapper;
import com.mms.catalogservice.repository.ProductRepository;
import com.mms.catalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductEntityMapper productEntityMapper;

    @Override
    public Product createProduct(Product product) {
        validateProduct(product);
        return productMapper.mapFromEntity(
                productRepository.save(productEntityMapper.map(product)));
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(product.getId());
        if(productEntityOptional.isEmpty()) {
            throw new NoSuchElementException("Update Product Failed because no product found for Id: " + product.getId());
        }
        ProductEntity productEntity = productEntityOptional.get();
        applyProductChanges(productEntity, product);
        productRepository.save(productEntityMapper.map(product));
        return product;
    }

    private void applyProductChanges(ProductEntity productDb, Product product) {
        productDb.setName(product.getName());
        productDb.setLongDescription(product.getFullDescription());
        productDb.setShortDescription(product.getShortDescription());
        productDb.setOnlineStatus(product.getOnlineStatus());
        //TODO: update Categories
    }

    @Override
    public void deleteProduct(Long id) {
        boolean productFound = productRepository.existsById(id);
        if(!productFound) {
            throw new NoSuchElementException("Delete Product Failed because no product found for Id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public boolean deleteProduct(String name) {
        boolean productFound = productRepository.existsByName(name);
        if(productFound) {
            throw new NoSuchElementException("Delete Product Failed because no product found for Name: " + name);
        }
        productRepository.deleteByName(name);
        return false;
    }

    @Override
    public List<Product> findAllProducts() {
        return productMapper.mapFromEntity(productRepository.findAll());
    }

    @Override
    public Product findProductById(Long id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        return productOptional.map(productEntity -> productMapper.mapFromEntity(productEntity)).orElse(null);
    }

    @Override
    public Product findProductByName(String name) {
        Optional<ProductEntity> productOptional = productRepository.findByName(name);
        return productOptional.map(productEntity -> productMapper.mapFromEntity(productEntity)).orElse(null);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    private void validateProduct(Product product) {
        //TODO: check categories availability
    }
}
