package com.mms.catalogservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
