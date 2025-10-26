package com.mms.catalogservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Category {
    public static final String PARENT_CATEGORY_DELIMITER = " -> ";
    @NotNull
    private long id;
    @NotBlank
    private String name;
    private Long parentId;
    private List<String> parentCategories;
}
