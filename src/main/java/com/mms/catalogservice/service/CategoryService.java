package com.mms.catalogservice.service;

import com.mms.catalogservice.model.Category;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface CategoryService {
    Category createCategory(Category Category) throws OperationNotSupportedException;
    Category updateCategory(Category Category);
    void deleteCategory(Long id);
    boolean deleteCategory(String name);
    List<Category> findAllCategories();
    Category findCategoryById(Long id);
    Category findCategoryByName(String name);
    List<String> getParentCategoryNames(Long id);
}
