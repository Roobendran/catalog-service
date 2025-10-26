package com.mms.catalogservice.controller;

import com.mms.catalogservice.dto.CategoryDTO;
import com.mms.catalogservice.dto.mappers.CategoryDTOMapper;
import com.mms.catalogservice.model.mappers.CategoryMapper;
//import com.mms.catalogservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {

//    @Autowired
//    CategoryService CategoryService;
//
//    @Autowired
//    CategoryDTOMapper CategoryDTOMapper;
//
//    @Autowired
//    CategoryMapper CategoryMapper;
//
//    @GetMapping
//    ResponseEntity<List<CategoryDTO>> getAllCategorys() {
//        return ResponseEntity.ok(CategoryDTOMapper.map(CategoryService.findAllCategorys()));
//    }
//
//    @PostMapping
//    ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO CategoryDTO) {
//        return ResponseEntity.ok(CategoryDTOMapper.map(CategoryService.createCategory(CategoryMapper.map(CategoryDTO))));
//    }
//
//    @PutMapping
//    ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO CategoryDTO) {
//        return ResponseEntity.ok(CategoryDTOMapper.map(CategoryService.createCategory(CategoryMapper.map(CategoryDTO))));
//    }
//
//    @GetMapping("/id/{id}")
//    ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
//        return ResponseEntity.ok(CategoryDTOMapper.map(CategoryService.findCategoryById(id)));
//    }
//
//    @GetMapping("/name/{name}")
//    ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
//        return ResponseEntity.ok(CategoryDTOMapper.map(CategoryService.findCategoryByName(name)));
//    }
//
//    @DeleteMapping("/name/{name}")
//    ResponseEntity<String> deleteCategory(@PathVariable String name) {
//        return ResponseEntity.ok(CategoryService.deleteCategory(name) ? "Category Deleted Successfully!" : "Category not Deleted!");
//    }
//
//    @DeleteMapping("/id/{id}")
//    ResponseEntity<String> deleteCategory(@PathVariable Long id) {
//        return ResponseEntity.ok(CategoryService.deleteCategory(id) ? "Category Deleted Successfully!" : "Category not Deleted!");
//    }
//
//    @GetMapping("/category/{category}")
//    ResponseEntity<List<CategoryDTO>> getCategoryByCategory(@PathVariable String category) {
//        return ResponseEntity.ok(CategoryDTOMapper.map(CategoryService.findCategoryByCategory(category)));
//    }
}
