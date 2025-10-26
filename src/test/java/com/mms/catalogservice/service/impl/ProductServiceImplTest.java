package com.mms.catalogservice.service.impl;

import com.mms.catalogservice.entity.ProductEntity;
import com.mms.catalogservice.entity.mappers.ProductEntityMapper;
import com.mms.catalogservice.model.Product;
import com.mms.catalogservice.model.mappers.ProductMapper;
import com.mms.catalogservice.mother.ProductEntityObjectMother;
import com.mms.catalogservice.mother.ProductObjectMother;
import com.mms.catalogservice.repository.ProductRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @Mock
    ProductEntityMapper productEntityMapper;

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
    void whenFindAllProductsCalledThenShouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(ProductEntityObjectMother.ANY));
        when(productMapper.mapFromEntity(anyList())).thenReturn(List.of(ProductObjectMother.ANY));

        List<Product> products = productServiceImpl.findAllProducts();

        Assert.assertEquals(products.size(), 1);
        Assert.assertEquals(products.get(0).getId(), ProductObjectMother.ANY.getId());
    }

    @Test
    void whenCreateProductCalledShouldSaveAndReturnProduct() {
        Product product = ProductObjectMother.ANY_RADIO_PRODUCT;

        when(productEntityMapper.map(product)).thenReturn(ProductEntityObjectMother.ANY);
        when(productRepository.save(ProductEntityObjectMother.ANY)).thenReturn(ProductEntityObjectMother.ANY);
        when(productMapper.mapFromEntity(ProductEntityObjectMother.ANY)).thenReturn(product);

        Product createdProduct = productServiceImpl.createProduct(product);

        Assert.assertNotNull(createdProduct);
        Assert.assertEquals(createdProduct.getName(), product.getName());

        verify(productRepository, times(1)).save(ProductEntityObjectMother.ANY);
    }

    @Test
    void whenFindProductByIdCalledWithValidIdShouldReturnProduct() {
        Long productId = ProductEntityObjectMother.ANY.getId();

        when(productRepository.findById(productId)).thenReturn(Optional.of(ProductEntityObjectMother.ANY));
        when(productMapper.mapFromEntity(ProductEntityObjectMother.ANY)).thenReturn(ProductObjectMother.ANY);

        Product foundProduct = productServiceImpl.findProductById(productId);

        Assert.assertNotNull(foundProduct);
        Assert.assertEquals(foundProduct.getId(), productId);
    }

    @Test
    void whenFindProductByIdCalledWithInvalidIdShouldThrowException() {
        Long invalidId = 999L;

        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

        productServiceImpl.findProductById(invalidId);

        verify(productMapper, never()).mapFromEntity((ProductEntity) any());

    }

    @Test
    void whenDeleteProductByIdCalledWithExistingIdShouldDelete() {
        Long existingId = 100L;

        when(productRepository.existsById(existingId)).thenReturn(true);

        doNothing().when(productRepository).deleteById(existingId);

        productServiceImpl.deleteProduct(existingId);

        verify(productRepository, times(1)).deleteById(existingId);
    }

    @Test(expectedExceptions = {NoSuchElementException.class})
    void whenDeleteProductByIdCalledWithNonExistingIdShouldThrowException() {
        Long nonExistingId = 999L;

        when(productRepository.existsById(nonExistingId)).thenReturn(false);

        productServiceImpl.deleteProduct(nonExistingId);

        verify(productRepository, never()).deleteById(anyLong());
    }

//    @Test
//    void whenFindProductByCategoryCalledShouldReturnFilteredProducts() {
//        String categoryName = "TV";
//        List<ProductEntity> entityList = List.of(ProductEntityObjectMother.ANY);
//
//        when(productRepository.findByCategory(categoryName)).thenReturn(entityList);
//        when(productMapper.toProductModel(ProductEntityObjectMother.ANY)).thenReturn(ProductObjectMother.ANY);
//
//        List<Product> products = productServiceImpl.findProductByCategory(categoryName);
//
//        Assert.assertFalse(products.isEmpty());
//        Assert.assertEquals(products.size(), 1);
//
//        verify(productRepository, times(1)).findByCategory(categoryName);
//    }
}