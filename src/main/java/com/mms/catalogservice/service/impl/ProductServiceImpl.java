package com.mms.catalogservice.service.impl;

import com.mms.catalogservice.entity.ProductEntity;
import com.mms.catalogservice.entity.mappers.ProductEntityMapper;
import com.mms.catalogservice.model.Category;
import com.mms.catalogservice.model.Product;
import com.mms.catalogservice.model.mappers.ProductMapper;
import com.mms.catalogservice.repository.ProductRepository;
import com.mms.catalogservice.service.CategoryService;
import com.mms.catalogservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductEntityMapper productEntityMapper;

    @Autowired
    CategoryService categoryService;

    @Override
    public Product createProduct(Product product) {
        validateProduct(product);
        logger.info("product categories {}", product.getAssociatedCategories());
        ProductEntity savedEntity = productRepository.save(productEntityMapper.mapToEntity(product));
        return productMapper.mapFromEntity(savedEntity);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(product.getId());
        if(productEntityOptional.isEmpty()) {
            throw new NoSuchElementException("Update Product Failed because no product found for Id: " + product.getId());
        }
        validateProduct(product);
        ProductEntity productEntity = productEntityOptional.get();
        applyProductChanges(productEntity, product);
        productRepository.save(productEntity);
        return product;
    }

    private void applyProductChanges(ProductEntity productDb, Product product) {
        ProductEntity productInput = productEntityMapper.mapToEntity(product);
        productDb.setLongDescription(productInput.getLongDescription());
        productDb.setShortDescription(productInput.getShortDescription());
        productDb.setOnlineStatus(productInput.getOnlineStatus());
        productDb.setCategories(productInput.getCategories());
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
        Product product = productOptional.map(productEntity -> productMapper.mapFromEntity(productEntity)).orElse(null);
        updateFullCategoryPath(product);
        return product;
    }

    @Override
    public Product findProductByName(String name) {
        Optional<ProductEntity> productOptional = productRepository.findByName(name);
        Product product = productOptional.map(productEntity -> productMapper.mapFromEntity(productEntity)).orElse(null);
        updateFullCategoryPath(product);
        return product;
    }

    private void updateFullCategoryPath(Product product) {
        if (Objects.isNull(product) || CollectionUtils.isEmpty(product.getAssociatedCategories())) return;
        product.getAssociatedCategories().forEach(category -> category.setParentCategories(categoryService.getParentCategoryNames(category.getId())));
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    private void validateProduct(Product product) {
        //Resolve Category
        if(!CollectionUtils.isEmpty(product.getAssociatedCategories())) {
            List<Category> resolvedCategories = product.getAssociatedCategories().stream().map(
                    cat -> {
                        Category resolvedCategory = categoryService.findCategoryById(cat.getId());
                        if(Objects.isNull(resolvedCategory))
                            throw new NoSuchElementException("Product: Name: "+ product.getName()+" couldn't be resolved because Associated Category not found for ID: " + cat.getId()+" Name: " + cat.getName());
                        return resolvedCategory;
                    }
            ).toList();
            product.setAssociatedCategories(resolvedCategories);
        }
    }
}
