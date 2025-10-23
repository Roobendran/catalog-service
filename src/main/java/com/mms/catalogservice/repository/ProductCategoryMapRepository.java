package com.mms.catalogservice.repository;

import com.mms.catalogservice.entity.ProductCategoryMapEntity;
import com.mms.catalogservice.entity.ProductCategoryMapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryMapRepository extends JpaRepository<ProductCategoryMapEntity, ProductCategoryMapId> {
}
