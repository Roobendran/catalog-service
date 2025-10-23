package com.mms.catalogservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="processed_data_set")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedDataSetEntity {
    @Id
    @Column(name = "file_name")
    private String fileName;
}
