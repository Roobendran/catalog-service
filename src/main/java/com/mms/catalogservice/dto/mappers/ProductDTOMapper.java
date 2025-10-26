package com.mms.catalogservice.dto.mappers;

import com.mms.catalogservice.dto.ProductDTO;
import com.mms.catalogservice.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper {
    ProductDTO map(Product product);
    List<ProductDTO> map(List<Product> products);
}
