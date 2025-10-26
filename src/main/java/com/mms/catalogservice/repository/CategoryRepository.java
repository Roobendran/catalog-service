package com.mms.catalogservice.repository;

import com.mms.catalogservice.entity.CategoryEntity;
import com.mms.catalogservice.model.CategoryPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);

//    @Query("SELECT c3.category_name, c2.category_name, c1.category_name \n" +
//            "FROM categories c3\n" +
//            "LEFT JOIN categories c2 ON c3.parent_category_id = c2.category_id\n" +
//            "LEFT JOIN categories c1 ON c2.parent_category_id = c1.category_id\n" +
//            "WHERE c3.category_id = 340;")

    @Query("SELECT new com.mms.catalogservice.model.CategoryPath(c3.id, c3.name, c2.id, c2.name, c1.id, c1.name) " +
            "FROM CategoryEntity c3 " +
            "LEFT JOIN c3.parentCategory c2 " +
            "LEFT JOIN c2.parentCategory c1 " +
            "WHERE c3.id = :id")
    List<CategoryPath> findParentCategoryPath(Long id);
}
