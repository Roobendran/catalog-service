package com.mms.catalogservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "categories")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "online_status")
    private OnlineStatus onlineStatus;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "short_description")
    private String shortDescription;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category_map",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
            )
    )
    private List<CategoryEntity> categories;
}
