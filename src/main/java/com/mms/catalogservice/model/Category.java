package com.mms.catalogservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {
    @NotNull
    private long id;
    @NotBlank
    private String name;
    private long parentId;
}
