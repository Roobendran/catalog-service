package com.mms.catalogservice.controller;

import com.mms.catalogservice.dto.ProductDTO;
import com.mms.catalogservice.dto.mappers.ProductDTOMapper;
import com.mms.catalogservice.model.mappers.ProductMapper;
import com.mms.catalogservice.mother.ProductDTOObjectMother;
import com.mms.catalogservice.mother.ProductObjectMother;
import com.mms.catalogservice.service.ProductService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @Mock
    ProductDTOMapper productDTOMapper;

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
    void whenGetAllProductsShouldReturnAllProducts() {

        when(productService.findAllProducts()).thenReturn(List.of(ProductObjectMother.ANY));
        when(productDTOMapper.map(anyList())).thenReturn(List.of(ProductDTOObjectMother.ANY));

        ResponseEntity<List<ProductDTO>> productsResponse = productController.getAllProducts();

        Assert.assertEquals(productsResponse.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(Objects.requireNonNull(productsResponse.getBody()).size(), 1);
        Assert.assertEquals(productsResponse.getBody().get(0).getId(), ProductObjectMother.ANY.getId());
    }

}
