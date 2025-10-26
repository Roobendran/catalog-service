package com.mms.catalogservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @Column(name = "category_id")
    private long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "parent_category_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", insertable = false, updatable = false)
    private CategoryEntity parentCategory;
}
