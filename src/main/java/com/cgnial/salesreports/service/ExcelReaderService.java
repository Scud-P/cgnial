package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelReaderService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelReaderService.class);

    public List<Product> readProductsExcelFile() throws IOException {
        String fileLocation = "src/main/resources/excels/products.xlsx";
        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        List<Product> products = new ArrayList<>();

        for (Row row : sheet) {
            // Skip the header row
            if (row.getRowNum() == 0) {
                continue;
            }

            if (isRowEmpty(row)) {
                break; // Stop the loop when reaching empty rows
            }

            Product product = new Product();

            // Check if the cell is not null before accessing it
            if (row.getCell(0) != null) {
                product.setCoutuCode((int) row.getCell(0).getNumericCellValue());
                logger.info("ExcelReader found Coutu code: {}", product.getCoutuCode());
            }
            if (row.getCell(1) != null) {
                product.setFrenchDescription(row.getCell(1).getStringCellValue());
                logger.info("ExcelReader found French Description: {}", product.getFrenchDescription());
            }
            if (row.getCell(2) != null) {
                product.setEnglishDescription(row.getCell(2).getStringCellValue());
                logger.info("ExcelReader found English Description: {}", product.getEnglishDescription());
            }
            if (row.getCell(3) != null) {
                product.setSize((int) row.getCell(3).getNumericCellValue());
                logger.info("ExcelReader found Size: {}", product.getSize());
            }
            if (row.getCell(4) != null) {
                product.setUom(row.getCell(4).getStringCellValue());
                logger.info("ExcelReader found UoM: {}", product.getUom());
            }
            if (row.getCell(5) != null) {
                product.setUnitsPerCase((int) row.getCell(5).getNumericCellValue());
                logger.info("ExcelReader found Units per Case: {}", product.getUnitsPerCase());
            }
            if (row.getCell(6) != null) {
                product.setSatauCode(row.getCell(6).getStringCellValue());
                logger.info("ExcelReader found Satau Code: {}", product.getSatauCode());
            }
            if (row.getCell(7) != null) {
                product.setUnfiCode(row.getCell(7).getStringCellValue());
                logger.info("ExcelReader found Unfi Code: {}", product.getUnfiCode());
            }
            if (row.getCell(8) != null) {
                product.setPuresourceCode(row.getCell(8).getStringCellValue());
                logger.info("ExcelReader found Puresource Code: {}", product.getPuresourceCode());
            }
            if (row.getCell(9) != null) {
                product.setUnitUpc(row.getCell(9).getStringCellValue());
                logger.info("ExcelReader found Unit UPC: {}", product.getUnitUpc());
            }
            if (row.getCell(10) != null) {
                product.setCaseUpc(row.getCell(10).getStringCellValue());
                logger.info("ExcelReader found Case UPC: {}", product.getCaseUpc());
            }
            if (row.getCell(11) != null) {
                product.setCaseSize(row.getCell(11).getStringCellValue());
                logger.info("ExcelReader found Case Size: {}", product.getCaseSize());
            }

            products.add(product);
            logger.info("Product added to list: {}", product);
        }

        workbook.close();
        file.close();
        return products;
    }

    private boolean isRowEmpty(Row row) {
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}
