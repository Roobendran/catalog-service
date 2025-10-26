package com.mms.catalogservice.mother;

import com.mms.catalogservice.model.Category;

import java.util.List;

public class CategoryObjectMother {

    public static final Category ANY = audioCategory();
    public static final Category AUDIO = audioCategory();
    public static final Category TV = tvCategory();

    public static Category tvCategory() {
        return Category.builder()
                .id(202L)
                .name("TV & Audio")
                .build();
    }

    public static Category audioCategory() {
        return Category.builder()
                .id(203L)
                .name("Fernseher")
                .parentId(202L)
                .build();
    }
    
    public static List<Category> fromIdString(String categoryIds) {
        return List.of(
            Category.builder().id(1165L).name("Category 1165").build(),
            Category.builder().id(784L).name("Category 784").build()
        );
    }
}