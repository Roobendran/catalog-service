package com.mms.catalogservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mms.catalogservice.dto.CategoryDTO;
import com.mms.catalogservice.dto.CategoryViews;
import com.mms.catalogservice.dto.mappers.CategoryDTOMapper;
import com.mms.catalogservice.model.mappers.CategoryMapper;
import com.mms.catalogservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import java.util.List;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryDTOMapper categoryDTOMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @JsonView(CategoryViews.Summary.class)
    @GetMapping
    ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.findAllCategories()));
    }

    @PostMapping
    ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO CategoryDTO) throws OperationNotSupportedException {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.createCategory(categoryMapper.mapFromDTO(CategoryDTO))));
    }

    @PutMapping
    ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO CategoryDTO) {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.updateCategory(categoryMapper.mapFromDTO(CategoryDTO))));
    }

    @GetMapping("/id/{id}")
    ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.findCategoryById(id)));
    }

    @GetMapping("/name/{name}")
    ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryDTOMapper.map(categoryService.findCategoryByName(name)));
    }

    @DeleteMapping("/name/{name}")
    ResponseEntity<String> deleteCategory(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.deleteCategory(name) ? "Category Deleted Successfully!" : "Category not Deleted!");
    }

    @DeleteMapping("/id/{id}")
    ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category Deleted Successfully!");
    }
}
