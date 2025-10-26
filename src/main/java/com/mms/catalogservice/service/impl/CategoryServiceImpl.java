package com.mms.catalogservice.service.impl;

import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.entity.mappers.CategoryEntityMapper;
import com.mms.catalogservice.model.Category;
import com.mms.catalogservice.model.mappers.CategoryMapper;
import com.mms.catalogservice.repository.CategoryRepository;
import com.mms.catalogservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryEntityMapper CategoryEntityMapper;

    @Override
    public Category createCategory(Category Category) throws OperationNotSupportedException {
        validateCategory(Category);
        return categoryMapper.mapFromEntity(
                categoryRepository.save(CategoryEntityMapper.map(Category)));
    }

    @Override
    public Category updateCategory(Category Category) {
        Optional<CategoryEntity> CategoryEntityOptional = categoryRepository.findById(Category.getId());
        if(CategoryEntityOptional.isEmpty()) {
            throw new NoSuchElementException("Update Category Failed because no Category found for Id: " + Category.getId());
        }
        CategoryEntity CategoryEntity = CategoryEntityOptional.get();
        applyCategoryChanges(CategoryEntity, Category);
        categoryRepository.save(CategoryEntityMapper.map(Category));
        return Category;
    }

    private void applyCategoryChanges(CategoryEntity CategoryDb, Category Category) {
        CategoryDb.setName(Category.getName());
        CategoryDb.setParentId(Category.getParentId());
        //TODO: update Categories
    }

    @Override
    public void deleteCategory(Long id) {
        boolean CategoryFound = categoryRepository.existsById(id);
        if(!CategoryFound) {
            throw new NoSuchElementException("Delete Category Failed because no Category found for Id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean deleteCategory(String name) {
        boolean CategoryFound = categoryRepository.existsByName(name);
        if(CategoryFound) {
            throw new NoSuchElementException("Delete Category Failed because no Category found for Name: " + name);
        }
        categoryRepository.deleteByName(name);
        return false;
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryMapper.mapFromEntity(categoryRepository.findAll());
    }

    @Override
    public Category findCategoryById(Long id) {
        Optional<CategoryEntity> CategoryOptional = categoryRepository.findById(id);
        return CategoryOptional.map(CategoryEntity -> categoryMapper.mapFromEntity(CategoryEntity)).orElse(null);
    }

    @Override
    public Category findCategoryByName(String name) {
        Optional<CategoryEntity> CategoryOptional = categoryRepository.findByName(name);
        return CategoryOptional.map(CategoryEntity -> categoryMapper.mapFromEntity(CategoryEntity)).orElse(null);
    }

    @Override
    public List<Category> getParentCategories(String category) {
        return null;
    }

    private void validateCategory(Category category) throws OperationNotSupportedException {
        //TODO: check categories availability
        if(categoryRepository.existsByName(category.getName()) ||
                categoryRepository.existsById(category.getId())) {
            throw new OperationNotSupportedException("Category Validation as the Category is already present");
        }

    }
}
