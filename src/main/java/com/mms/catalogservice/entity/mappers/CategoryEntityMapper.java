package com.mms.catalogservice.entity.mappers;

import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    @Mapping(target = "parentCategory", ignore = true)
    CategoryEntity map(Category category);
}
