package com.mms.catalogservice.model;

import com.mms.catalogservice.entity.OnlineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Product {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private OnlineStatus onlineStatus;
    private String fullDescription;
    private String shortDescription;
    private List<Category> associatedCategories;
}
