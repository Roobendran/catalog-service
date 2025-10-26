package com.mms.catalogservice.service.impl;

import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.entity.mappers.CategoryEntityMapper;
import com.mms.catalogservice.model.Category;
import com.mms.catalogservice.model.CategoryPath;
import com.mms.catalogservice.model.mappers.CategoryMapper;
import com.mms.catalogservice.repository.CategoryRepository;
import com.mms.catalogservice.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService {

    public static final String DEFAULT_ROOT_CATEGORY = "MediaMarkt_DE";
    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryEntityMapper categoryEntityMapper;

    @Override
    public Category createCategory(Category category) throws OperationNotSupportedException {
        validateCategory(category);
        categoryRepository.save(categoryEntityMapper.map(category));
        Category categoryDB = findCategoryByName(category.getName());
        logger.info("Category Created Id: {}",categoryDB.getId());
        fillParentCategoryNames(categoryDB);
        return categoryDB;
    }

    @Override
    public Category updateCategory(Category category) {
        Optional<CategoryEntity> CategoryEntityOptional = categoryRepository.findById(category.getId());
        if(CategoryEntityOptional.isEmpty()) {
            throw new NoSuchElementException("Update category Failed because no category found for Id: " + category.getId());
        }
        CategoryEntity CategoryEntity = CategoryEntityOptional.get();
        applyCategoryChanges(CategoryEntity, category);
        categoryRepository.save(categoryEntityMapper.map(category));
        fillParentCategoryNames(category);
        return category;
    }

    private void applyCategoryChanges(CategoryEntity categoryDb, Category category) {
        categoryDb.setName(category.getName());
        categoryDb.setParentId(category.getParentId());
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
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(id);
        Category category = categoryOptional.map(categoryEntity -> categoryMapper.mapFromEntity(categoryEntity))
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
        fillParentCategoryNames(category);
        return category;
    }

    @Override
    public Category findCategoryByName(String name) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findByName(name);
        Category category = categoryOptional.map(categoryEntity -> categoryMapper.mapFromEntity(categoryEntity))
                .orElseThrow(() -> new NoSuchElementException("Category not found with name: " + name));
        fillParentCategoryNames(category);
        return category;
    }

    @Override
    public List<String> getParentCategoryNames(Long id) {
        logger.info("Getting 3 Parent Category names for Category Id: {}", id );
        List<CategoryPath> parentCategories = categoryRepository.findParentCategoryPath(id);

        logger.info("Fetched 3 Parent Categories for Category Id: {} is {}", id, parentCategories );
        List<String> result = parentCategories.stream()
                .map(cp -> Stream.of(cp.getImmediateSecondParentName(), cp.getImmediateFirstParentName(), cp.getCurrentName())
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(Category.PARENT_CATEGORY_DELIMITER))
                )
                .collect(Collectors.toCollection(ArrayList::new));

        if (result.size() < 3) {
            result.add(0, DEFAULT_ROOT_CATEGORY);
        }
        return result;
    }

    private void fillParentCategoryNames(Category category) {
        if(Objects.isNull(category)) return;
        category.setParentCategories(getParentCategoryNames(category.getId()));
    }

    private void validateCategory(Category category) throws OperationNotSupportedException {
        if(categoryRepository.existsByName(category.getName()) ||
                categoryRepository.existsById(category.getId())) {
            throw new OperationNotSupportedException("Category Validation failed as the Category is already present");
        }
        if(category.getParentId() != null && !categoryRepository.existsById(category.getParentId())) {
            throw new OperationNotSupportedException("Category Validation failed as the Parent Category is not present");
        }
    }
}
