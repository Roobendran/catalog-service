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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

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
        category = categoryMapper.mapFromEntity(
                categoryRepository.save(categoryEntityMapper.map(category)));
        fillParentCategoryNames(category);
        return category;
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
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(id);
        Category category = categoryOptional.map(categoryEntity -> categoryMapper.mapFromEntity(categoryEntity)).orElse(null);
        fillParentCategoryNames(category);
        return category;
    }

    @Override
    public Category findCategoryByName(String name) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findByName(name);
        Category category = categoryOptional.map(categoryEntity -> categoryMapper.mapFromEntity(categoryEntity)).orElse(null);
        fillParentCategoryNames(category);
        return category;
    }

    @Override
    public List<String> getParentCategoryNames(Long id) {
        logger.info("Getting 3 Parent Category names for Category Id: {}", id );
        List<CategoryPath> parentCategories = categoryRepository.findParentCategoryPath(id);
        logger.info("Fetched 3 Parent Categories for Category Id: {} is {}", id, parentCategories );
        return parentCategories.stream().map(cp -> String.join(Category.PARENT_CATEGORY_DELIMITER,
                List.of(cp.getImmediateSecondParentName(), cp.getImmediateFirstParentName(), cp.getCurrentName())))
                .toList();
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
