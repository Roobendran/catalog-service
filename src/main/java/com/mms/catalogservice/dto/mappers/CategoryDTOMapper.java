package com.mms.catalogservice.dto.mappers;

import com.mms.catalogservice.dto.CategoryDTO;
import com.mms.catalogservice.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryDTOMapper {
    CategoryDTO map(Category category);
    List<CategoryDTO> map(List<Category> categories);
}
