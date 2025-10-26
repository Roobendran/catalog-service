package com.mms.catalogservice.model.mappers;

import com.mms.catalogservice.dto.ProductDTO;
import com.mms.catalogservice.entity.ProductEntity;
import com.mms.catalogservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "fullDescription", source = "fullDescription")
    Product map(ProductDTO productDTO);
    List<Product> map(List<ProductDTO> productDTOs);

    @Mapping(target = "fullDescription", source = "longDescription")
    Product map(ProductEntity productEntity);
}
