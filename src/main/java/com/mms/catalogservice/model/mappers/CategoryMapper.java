package com.mms.catalogservice.model.mappers;

import com.mms.catalogservice.dto.CategoryDTO;
import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentCategories", source = "fullCategoryPath", qualifiedByName = "fullPathToCategoriesNameList")
    Category mapFromDTO(CategoryDTO categoryDTO);
    List<Category> mapFromDTO(List<CategoryDTO> categoryDTO);

    @Mapping(target = "parentCategories", ignore = true)
    Category mapFromEntity(CategoryEntity categoryEntity);
    List<Category> mapFromEntity(List<CategoryEntity> categoryEntity);

    @Named("fullPathToCategoriesNameList")
    static List<String> fullPathToCategoriesNameList(String categories) {
        if(categories == null) return null;
        return Arrays.asList(categories.split(Category.PARENT_CATEGORY_DELIMITER));
    }
}
