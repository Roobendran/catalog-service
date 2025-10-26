package com.mms.catalogservice.entity.mappers;

import com.mms.catalogservice.entity.ProductEntity;
import com.mms.catalogservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {
    @Mapping(target = "longDescription", source = "fullDescription")
    ProductEntity map(Product product);
    List<ProductEntity> map(List<Product> products);
}
