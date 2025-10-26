package com.mms.catalogservice.controller;

import com.mms.catalogservice.dto.ProductDTO;
import com.mms.catalogservice.dto.mappers.ProductDTOMapper;
import com.mms.catalogservice.model.mappers.ProductMapper;
import com.mms.catalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductDTOMapper productDTOMapper;

    @Autowired
    ProductMapper productMapper;

    @GetMapping
    ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productDTOMapper.map(productService.findAllProducts()));
    }

    @PostMapping
    ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productDTOMapper.map(productService.createProduct(productMapper.map(productDTO))));
    }

    @PutMapping
    ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productDTOMapper.map(productService.createProduct(productMapper.map(productDTO))));
    }

    @GetMapping("/id/{id}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productDTOMapper.map(productService.findProductById(id)));
    }

    @GetMapping("/name/{name}")
    ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productDTOMapper.map(productService.findProductByName(name)));
    }

    @DeleteMapping("/name/{name}")
    ResponseEntity<String> deleteProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.deleteProduct(name) ? "Product Deleted Successfully!" : "Product not Deleted!");
    }

    @DeleteMapping("/id/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id) ? "Product Deleted Successfully!" : "Product not Deleted!");
    }

    @GetMapping("/category/{category}")
    ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productDTOMapper.map(productService.findProductByCategory(category)));
    }
}
