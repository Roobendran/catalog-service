package com.mms.catalogservice.dto;

import com.mms.catalogservice.entity.OnlineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private OnlineStatus onlineStatus;
    private String fullDescription;
    private String shortDescription;
//    private List<CategoryDTO> associatedCategories;
}
