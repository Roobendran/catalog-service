package com.mms.catalogservice.entity.mappers;

import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    CategoryEntity map(Category category);
}
