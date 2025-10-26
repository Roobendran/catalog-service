package com.mms.catalogservice.service.impl;

import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.entity.mappers.CategoryEntityMapper;
import com.mms.catalogservice.model.Category;
import com.mms.catalogservice.model.mappers.CategoryMapper;
import com.mms.catalogservice.mother.CategoryEntityObjectMother;
import com.mms.catalogservice.mother.CategoryObjectMother;
import com.mms.catalogservice.repository.CategoryRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryMapper categoryMapper;

    @Mock
    CategoryEntityMapper categoryEntityMapper;

    private AutoCloseable autoCloseable;

    @BeforeMethod
    public void setUp() {
        autoCloseable = openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void whenFindAllCategorysCalledThenShouldReturnAllCategorys() {
        when(categoryRepository.findAll()).thenReturn(List.of(CategoryEntityObjectMother.ANY));
        when(categoryMapper.mapFromEntity(anyList())).thenReturn(List.of(CategoryObjectMother.ANY));

        List<Category> Categorys = categoryServiceImpl.findAllCategories();

        Assert.assertEquals(Categorys.size(), 1);
        Assert.assertEquals(Categorys.get(0).getId(), CategoryObjectMother.ANY.getId());
    }

    @Test
    void whenCreateCategoryCalledShouldSaveAndReturnCategory() throws OperationNotSupportedException {
        Category category = CategoryObjectMother.ANY;

        when(categoryEntityMapper.map(category)).thenReturn(CategoryEntityObjectMother.ANY);
        when(categoryRepository.save(CategoryEntityObjectMother.ANY)).thenReturn(CategoryEntityObjectMother.ANY);
        when(categoryRepository.existsById(CategoryEntityObjectMother.ANY.getParentId())).thenReturn (true);
        when(categoryMapper.mapFromEntity(CategoryEntityObjectMother.ANY)).thenReturn(category);

        Category createdCategory = categoryServiceImpl.createCategory(category);

        Assert.assertNotNull(createdCategory);
        Assert.assertEquals(createdCategory.getName(), category.getName());

        verify(categoryRepository, times(1)).save(CategoryEntityObjectMother.ANY);
    }

    @Test
    void whenFindCategoryByIdCalledWithValidIdShouldReturnCategory() {
        Long CategoryId = CategoryEntityObjectMother.ANY.getId();

        when(categoryRepository.findById(CategoryId)).thenReturn(Optional.of(CategoryEntityObjectMother.ANY));
        when(categoryMapper.mapFromEntity(CategoryEntityObjectMother.ANY)).thenReturn(CategoryObjectMother.ANY);

        Category foundCategory = categoryServiceImpl.findCategoryById(CategoryId);

        Assert.assertNotNull(foundCategory);
        Assert.assertEquals(foundCategory.getId(), CategoryId);
    }

    @Test
    void whenFindCategoryByIdCalledWithInvalidIdShouldThrowException() {
        Long invalidId = 999L;

        when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());

        categoryServiceImpl.findCategoryById(invalidId);

        verify(categoryMapper, never()).mapFromEntity((CategoryEntity) any());

    }

    @Test
    void whenDeleteCategoryByIdCalledWithExistingIdShouldDelete() {
        Long existingId = 100L;

        when(categoryRepository.existsById(existingId)).thenReturn(true);

        doNothing().when(categoryRepository).deleteById(existingId);

        categoryServiceImpl.deleteCategory(existingId);

        verify(categoryRepository, times(1)).deleteById(existingId);
    }

    @Test(expectedExceptions = {NoSuchElementException.class})
    void whenDeleteCategoryByIdCalledWithNonExistingIdShouldThrowException() {
        Long nonExistingId = 999L;

        when(categoryRepository.existsById(nonExistingId)).thenReturn(false);

        categoryServiceImpl.deleteCategory(nonExistingId);

        verify(categoryRepository, never()).deleteById(anyLong());
    }
}