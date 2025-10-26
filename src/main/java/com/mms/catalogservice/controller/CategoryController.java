package com.mms.catalogservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mms.catalogservice.dto.CategoryDTO;
import com.mms.catalogservice.dto.CategoryViews;
import com.mms.catalogservice.dto.mappers.CategoryDTOMapper;
import com.mms.catalogservice.model.mappers.CategoryMapper;
import com.mms.catalogservice.service.CategoryService;
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

import javax.naming.OperationNotSupportedException;
import java.util.List;

@Tag(name = "Category Management", description = "API for managing and retrieving product categories, including hierarchical data.")
@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryDTOMapper categoryDTOMapper;

    @Autowired
    CategoryMapper categoryMapper;

    // --- GET All Categories (Summary View) ---
    @Operation(summary = "Retrieve all categories (Summary View)", description = "Fetches a summarized list of all categories. Uses Jackson's @JsonView for minimal data exposure.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list (Summary View)",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(CategoryViews.Summary.class)
    ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.findAllCategories()));
    }

    // --- CREATE Category ---
    @Operation(summary = "Create a new category", description = "Adds a new category entry to the database.")
    @ApiResponse(responseCode = "200", description = "Category created successfully", content = @Content(schema = @Schema(implementation = CategoryDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input or operation not supported")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO CategoryDTO) throws OperationNotSupportedException {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.createCategory(categoryMapper.mapFromDTO(CategoryDTO))));
    }

    // --- UPDATE Category ---
    @Operation(summary = "Update an existing category", description = "Modifies the details of an existing category.")
    @ApiResponse(responseCode = "200", description = "Category updated successfully", content = @Content(schema = @Schema(implementation = CategoryDTO.class)))
    @ApiResponse(responseCode = "404", description = "Category not found")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO CategoryDTO) {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.updateCategory(categoryMapper.mapFromDTO(CategoryDTO))));
    }

    // --- GET Category by ID ---
    @Operation(summary = "Get category by ID", description = "Retrieves a single category using its unique database ID.")
    @ApiResponse(responseCode = "200", description = "Category found", content = @Content(schema = @Schema(implementation = CategoryDTO.class)))
    @ApiResponse(responseCode = "404", description = "Category not found with the given ID")
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryDTO> getCategoryById(
            @Parameter(description = "The unique ID of the category.")
            @PathVariable Long id) {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.findCategoryById(id)));
    }

    // --- GET Category by Name ---
    @Operation(summary = "Get category by name", description = "Retrieves a single category using its unique name.")
    @ApiResponse(responseCode = "200", description = "Category found", content = @Content(schema = @Schema(implementation = CategoryDTO.class)))
    @ApiResponse(responseCode = "404", description = "Category not found with the given name")
    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryDTO> getCategoryByName(
            @Parameter(description = "The unique name of the category.")
            @PathVariable String name) {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.findCategoryByName(name)));
    }

    // --- DELETE Category by Name ---
    @Operation(summary = "Delete category by name", description = "Deletes a category using its unique name.")
    @ApiResponse(responseCode = "200", description = "Deletion status message")
    @DeleteMapping("/name/{name}")
    ResponseEntity<String> deleteCategory(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.deleteCategory(name) ? "Category Deleted Successfully!" : "Category not Deleted!");
    }

    // --- DELETE Category by ID ---
    @Operation(summary = "Delete category by ID", description = "Deletes a category using its unique ID.")
    @ApiResponse(responseCode = "200", description = "Deletion status message")
    @DeleteMapping("/id/{id}")
    ResponseEntity<String> deleteCategory(@Parameter(description = "The unique ID of the category.") @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category Deleted Successfully!");
    }
}