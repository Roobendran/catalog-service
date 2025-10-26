package com.mms.catalogservice.repository;

import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.model.CategoryPath;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
    @Query("SELECT new com.mms.catalogservice.model.CategoryPath(c3.id, c3.name, c2.id, c2.name, c1.id, c1.name) " +
            "FROM CategoryEntity c3 " +
            "LEFT JOIN c3.parentCategory c2 " +
            "LEFT JOIN c2.parentCategory c1 " +
            "WHERE c3.id = :id")
    List<CategoryPath> findParentCategoryPath(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CategoryEntity c SET c.parentId = :parentId WHERE c.id = :id")
    void updateParentId(@Param("id") Long id, @Param("parentId") Long parentId);
}
