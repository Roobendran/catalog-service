package com.mms.catalogservice.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDTO {
    @NotNull
    @JsonView(CategoryViews.Summary.class)
    private long id;

    @NotBlank
    @JsonView(CategoryViews.Summary.class)
    private String name;

    @JsonView(CategoryViews.Summary.class)
    private Long parentId;

    @JsonView(CategoryViews.Detail.class)
    private String fullCategoryPath;
}
