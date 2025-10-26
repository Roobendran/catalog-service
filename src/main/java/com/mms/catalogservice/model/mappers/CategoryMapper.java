package com.mms.catalogservice.model.mappers;

import com.mms.catalogservice.dto.CategoryDTO;
import com.mms.catalogservice.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category map(CategoryDTO categoryDTO);
}
