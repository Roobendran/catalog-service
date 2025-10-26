package com.mms.catalogservice.loader;

import com.mms.catalogservice.entity.*;
import com.mms.catalogservice.repository.CategoryRepository;
import com.mms.catalogservice.repository.ProcessedDataSetRepository;
import com.mms.catalogservice.repository.ProductCategoryMapRepository;
import com.mms.catalogservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class XLSXDataLoader implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryMapRepository productCategoryMapRepository;

    @Autowired
    private ProcessedDataSetRepository processedDataSetRepository;

    private static final Logger logger = LoggerFactory.getLogger(XLSXDataLoader.class);

    @Override
    public void run(String... args) throws Exception {
        loadCsvFiles("classpath:import/*.xlsx");
    }

    private void loadCsvFiles(String pattern) throws Exception {
        logger.info("Loading CSV files from folder: import");
        var resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(pattern);

        for (Resource resource : resources) {
            String filename = resource.getFilename();
            logger.info(String.format("Fetched XLSX file Name: %s", filename));

            if(processedDataSetRepository.existsByFileName(filename)) {
                logger.info(String.format("Already processed XLSX file Name: %s", filename));
                continue;
            }

            if (filename != null && filename.startsWith("categories")) {
                loadCategories(resource);
            } else if (filename != null && filename.startsWith("products")) {
                loadProducts(resource);
            }

            processedDataSetRepository.save(ProcessedDataSetEntity.builder().fileName(filename).build());
        }
    }

    private void loadCategories(Resource resource) throws Exception {
        logger.info("Loading categories!");
        try (Workbook workbook = new XSSFWorkbook(resource.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            DataFormatter dataFormatter = new DataFormatter();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String idStr = dataFormatter.formatCellValue(row.getCell(0));
                String name = dataFormatter.formatCellValue(row.getCell(1));
                String parentIdStr = dataFormatter.formatCellValue(row.getCell(2));

                Long id = Long.parseLong(idStr.trim());
                Long parentId = parentIdStr.equalsIgnoreCase("NULL") || parentIdStr.isBlank() || !StringUtils.isNumeric(parentIdStr)
                        ? null
                        : Long.parseLong(parentIdStr.trim());

                CategoryEntity category = CategoryEntity.builder()
                        .id(id)
                        .name(name)
                        .parentId(parentId)
                        .build();
                if (!categoryRepository.existsById(category.getId())) {
                    logger.info("Saving category {}", category);
                    CategoryEntity categoryEntityDb = categoryRepository.save(category);
                    logger.info("Saved category {}", categoryEntityDb);

                    logger.info("*****");
                }
            }
        }
    }

    private void loadProducts(Resource resource) throws Exception {
        logger.info("Loading products!");
        try (Workbook workbook = new XSSFWorkbook(resource.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String name = dataFormatter.formatCellValue(row.getCell(0));
                String categoryIdsRaw = dataFormatter.formatCellValue(row.getCell(1));
                String onlineStatusStr = dataFormatter.formatCellValue(row.getCell(2));
                String longDesc = dataFormatter.formatCellValue(row.getCell(3));
                String shortDesc = dataFormatter.formatCellValue(row.getCell(4));

                ProductEntity product = ProductEntity.builder()
                        .name(name)
                        .onlineStatus(OnlineStatus.valueOf(onlineStatusStr.trim()))
                        .longDescription(longDesc.trim())
                        .shortDescription(shortDesc.trim())
                        .categories(loadProductCategories(categoryIdsRaw))
                        .build();

                if (!productRepository.existsByName(product.getName())) {
                    logger.info("Saving product! {}", product);
                    Long productId = productRepository.save(product).getId();
                    logger.info("Saved product! {}", product);

                    logger.info("*****");
//                    loadProductCategoryMap(productId, categoryIdsRaw);
                }
            }
        }
    }

    private List<CategoryEntity> loadProductCategories(String categoryIdsRaw) {
        if (StringUtils.isEmpty(categoryIdsRaw))
            return null;
        logger.info("Loading ProductCategories!");
        List<CategoryEntity> categories = new ArrayList<>();
        for (String categoryIdStr : categoryIdsRaw.split(";")) {
            if (StringUtils.isNumeric(categoryIdStr)) {
                Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(Long.parseLong(categoryIdStr));
                categoryEntityOptional.ifPresent(categories::add);
            }
        }
        return categories;
    }


    private void loadProductCategoryMap(Long productId, String categoryIdsRaw) {
        if (StringUtils.isEmpty(categoryIdsRaw))
            return;
        logger.info("Loading ProductCategoryMapping!");
        for(String categoryIdStr : categoryIdsRaw.split(";")) {
            if(StringUtils.isNumeric(categoryIdStr)) {
                ProductCategoryMapId productCategoryMapId = ProductCategoryMapId.builder()
                        .productId(productId)
                        .categoryId(Long.parseLong(categoryIdStr))
                        .build();
                ProductCategoryMapEntity productCategoryMapEntity = ProductCategoryMapEntity.builder()
                        .productCategoryMapId(productCategoryMapId)
                        .build();
                if(!productCategoryMapRepository.existsById(productCategoryMapId)) {
                    logger.info("Saving ProductCategoryMap!");
                    productCategoryMapRepository.save(productCategoryMapEntity);
                }
            }
        }
    }
}
