package com.mms.catalogservice.model.mappers;

import com.mms.catalogservice.dto.ProductDTO;
import com.mms.catalogservice.dto.mappers.CategoryDTOMapper;
import com.mms.catalogservice.entity.ProductEntity;
import com.mms.catalogservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CategoryDTOMapper.class})
public interface ProductMapper {

    @Mapping(target = "fullDescription", source = "fullDescription")
    Product mapFromDTO(ProductDTO productDTO);
    List<Product> mapFromDTO(List<ProductDTO> productDTOs);

    @Mapping(target = "associatedCategories", source = "categories")
    @Mapping(target = "fullDescription", source = "longDescription")
    Product mapFromEntity(ProductEntity productEntity);

    List<Product> mapFromEntity(List<ProductEntity> productEntities);
}
