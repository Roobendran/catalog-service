package com.mms.catalogservice.dto.mappers;

import com.mms.catalogservice.dto.CategoryDTO;
import com.mms.catalogservice.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryDTOMapper {
    CategoryDTO map(Category category);
}
