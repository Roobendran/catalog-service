package com.mms.catalogservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product_category_map")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryMapEntity {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride( name = "productId", column = @Column(name = "product_id")),
            @AttributeOverride( name = "categoryId", column = @Column(name = "category_id")),
    })
    ProductCategoryMapId productCategoryMapId;
}
