package com.mms.catalogservice.repository;

import com.mms.catalogservice.entity.ProcessedDataSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedDataSetRepository extends JpaRepository<ProcessedDataSetEntity, String> {
    boolean existsByFileName(String filename);
}
