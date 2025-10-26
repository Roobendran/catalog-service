package com.mms.catalogservice.controller;

import com.mms.catalogservice.dto.ProductDTO;
import com.mms.catalogservice.dto.mappers.ProductDTOMapper;
import com.mms.catalogservice.model.mappers.ProductMapper;
import com.mms.catalogservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Management", description = "API for creating, retrieving, updating, and deleting catalog products.")
@RequestMapping("/api/products")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductDTOMapper productDTOMapper;

    @Autowired
    ProductMapper productMapper;

    @Operation(summary = "Retrieve all products", description = "Fetches a complete list of all products in the catalog.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productDTOMapper.map(productService.findAllProducts()));
    }

    @Operation(summary = "Create a new product", description = "Adds a new product entry to the catalog.")
    @ApiResponse(responseCode = "200", description = "Product created successfully", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input or data already exists")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productDTOMapper.map(productService.createProduct(productMapper.mapFromDTO(productDTO))));
    }

    @Operation(summary = "Update an existing product", description = "Modifies the details of an existing product based on the provided DTO.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @ApiResponse(responseCode = "404", description = "Product not found")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productDTOMapper.map(productService.updateProduct(productMapper.mapFromDTO(productDTO))));
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a single product using its unique database ID.")
    @ApiResponse(responseCode = "200", description = "Product found", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @ApiResponse(responseCode = "404", description = "Product not found with the given ID")
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "The unique ID of the product.")
            @PathVariable Long id) {
        return ResponseEntity.ok(productDTOMapper.map(productService.findProductById(id)));
    }

    @Operation(summary = "Get product by name", description = "Retrieves a single product using its unique name.")
    @ApiResponse(responseCode = "200", description = "Product found", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @ApiResponse(responseCode = "404", description = "Product not found with the given name")
    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDTO> getProductByName(
            @Parameter(description = "The unique name of the product.")
            @PathVariable String name) {
        return ResponseEntity.ok(productDTOMapper.map(productService.findProductByName(name)));
    }

    @Operation(summary = "Delete product by name", description = "Deletes a product using its unique name.")
    @ApiResponse(responseCode = "200", description = "Deletion status message")
    @DeleteMapping("/name/{name}")
    ResponseEntity<String> deleteProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.deleteProduct(name) ? "Product Deleted Successfully!" : "Product not Deleted!");
    }

    @Operation(summary = "Delete product by ID", description = "Deletes a product using its unique ID.")
    @ApiResponse(responseCode = "200", description = "Deletion status message")
    @DeleteMapping("/id/{id}")
    ResponseEntity<String> deleteProduct(@Parameter(description = "The unique ID of the product.") @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product Deleted Successfully");
    }

    @Operation(summary = "Get products by category", description = "Retrieves a list of products associated with a specific category name.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))
    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ProductDTO>> getProductByCategory(
            @Parameter(description = "The name of the category to filter by.")
            @PathVariable String category) {
        return ResponseEntity.ok(productDTOMapper.map(productService.findProductByCategory(category)));
    }
}