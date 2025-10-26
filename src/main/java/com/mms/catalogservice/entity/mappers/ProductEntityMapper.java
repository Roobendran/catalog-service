package com.mms.catalogservice.entity.mappers;

import com.mms.catalogservice.entity.ProductEntity;
import com.mms.catalogservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryEntityMapper.class)
public interface ProductEntityMapper {
    @Mapping(target = "categories", source = "associatedCategories")
    @Mapping(target = "longDescription", source = "fullDescription")
    ProductEntity mapToEntity(Product product);
    List<ProductEntity> mapToEntity(List<Product> products);
}
