package com.mms.catalogservice.repository;

import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}
