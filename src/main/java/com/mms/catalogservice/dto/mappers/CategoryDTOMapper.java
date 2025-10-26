package com.mms.catalogservice.dto.mappers;

import com.mms.catalogservice.dto.CategoryDTO;
import com.mms.catalogservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryDTOMapper {
    @Mapping( target = "fullCategoryPath",  source = "parentCategories", qualifiedByName = "categoriesNameListToFullPath")
    CategoryDTO map(Category category);
    List<CategoryDTO> map(List<Category> categories);

    @Named("categoriesNameListToFullPath")
    static String categoriesNameListToFullPath(List<String> categories) {
        return CollectionUtils.isEmpty(categories) ? null : String.join(Category.PARENT_CATEGORY_DELIMITER, categories);
    }
}
