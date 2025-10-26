package com.mms.catalogservice.mother;

import com.mms.catalogservice.entity.CategoryEntity;

import java.util.List;

public class CategoryEntityObjectMother {

    public static final CategoryEntity ANY = audioCategory();
    public static final CategoryEntity AUDIO = audioCategory();
    public static final CategoryEntity TV = tvCategory();

    public static CategoryEntity tvCategory() {
        return CategoryEntity.builder()
                .id(202L)
                .name("TV & Audio")
                .build();
    }

    public static CategoryEntity audioCategory() {
        return CategoryEntity.builder()
                .id(203L)
                .name("Fernseher")
                .parentId(202L)
                .build();
    }
    
    public static List<CategoryEntity> fromIdString(String categoryIds) {
        return List.of(
            CategoryEntity.builder().id(1165L).name("CategoryEntity 1165").build(),
            CategoryEntity.builder().id(784L).name("CategoryEntity 784").build()
        );
    }
}