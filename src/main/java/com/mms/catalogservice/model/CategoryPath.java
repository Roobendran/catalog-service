package com.mms.catalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
@Getter
public class CategoryPath {
    private Long currentId;
    private String currentName;
    private Long immediateFirstParentId;
    private String immediateFirstParentName;
    private Long immediateSecondParentId;
    private String immediateSecondParentName;
}
