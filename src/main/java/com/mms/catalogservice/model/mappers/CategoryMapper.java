package com.mms.catalogservice.model.mappers;

import com.mms.catalogservice.dto.CategoryDTO;
import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapFromDTO(CategoryDTO categoryDTO);
    List<Category> mapFromDTO(List<CategoryDTO> categoryDTO);

    Category mapFromEntity(CategoryEntity categoryEntity);
    List<Category> mapFromEntity(List<CategoryEntity> categoryEntity);
}
