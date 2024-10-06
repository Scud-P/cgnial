package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.Product;
import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.domain.PurchaseOrderProduct;
import com.cgnial.salesreports.domain.parameter.PuresourcePOSParameter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


    public List<PurchaseOrder> readPurchaseOrdersExcelFile() throws IOException {
        String fileLocation = "src/main/resources/excels/pomaster.xlsx";
        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        List<PurchaseOrder> pos = new ArrayList<>();

        for (Row row : sheet) {
            // Skip the header row
            if (row.getRowNum() == 0) {
                continue;
            }

            if (isRowEmpty(row)) {
                break;
            }
            PurchaseOrder po = new PurchaseOrder();
            if (row.getCell(0) != null) {
                po.setPoDate(row.getCell(0).getStringCellValue());
            }
            if (row.getCell(1) != null) {
                po.setDistributor(row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2) != null) {
                po.setAmount((int) row.getCell(2).getNumericCellValue());
            }
            logger.info("ExcelReader found PO: {}", po);
            pos.add(po);
        }
        return pos;
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

    public List<PuresourcePOSParameter> readPuresourcePOSParameters() throws IOException {
        String fileLocation = "src/main/resources/excels/puresource.xlsx";
        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        List<PuresourcePOSParameter> sales = new ArrayList<>();

        // Define which columns to skip by their index (mm/yy, customer number, brand code, product description)
        Set<Integer> columnsToSkip = new HashSet<>(Arrays.asList(2, 3, 9, 11));

        for (Row row : sheet) {
            // Skip the header row
            if (row.getRowNum() == 0) {
                continue;
            }

            if (isRowEmpty(row)) {
                break;
            }

            PuresourcePOSParameter po = new PuresourcePOSParameter();

            // Track the current index of the cell to process
            int actualColumnIndex = 0;

            for (int colIndex = 0; colIndex <= row.getLastCellNum(); colIndex++) {
                // Check if the current column index is in the skip list
                if (columnsToSkip.contains(colIndex)) {
                    logger.info("Skipping column {} as per configuration.", colIndex);
                    continue;  // Skip this column
                }

                Cell cell = row.getCell(colIndex);
                if (cell != null) {
                    // Only process non-skipped columns
                    switch (actualColumnIndex) {
                        case 0: // Year
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setYear((int) cell.getNumericCellValue());
                                logger.info("Found Year: {}", po.getYear());
                            }
                            break;
                        case 1: // Month
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setMonth((int) cell.getNumericCellValue());
                                logger.info("Found Month: {}", po.getMonth());
                            }
                            break;
                        case 2: // Customer Name (this is now the 3rd column after skipping)
                            if (cell.getCellType() == CellType.STRING) {
                                po.setCustomerName(cell.getStringCellValue());
                                logger.info("Found Customer Name: {}", po.getCustomerName());
                            }
                            break;
                        case 3: // Address
                            if (cell.getCellType() == CellType.STRING) {
                                po.setAddress(cell.getStringCellValue());
                                logger.info("Found Address: {}", po.getAddress());
                            }
                            break;
                        case 4: // City
                            if (cell.getCellType() == CellType.STRING) {
                                po.setCity(cell.getStringCellValue());
                                logger.info("Found City: {}", po.getCity());
                            }
                            break;
                        case 5: // Province
                            if (cell.getCellType() == CellType.STRING) {
                                po.setProvince(cell.getStringCellValue());
                                logger.info("Found Province: {}", po.getProvince());
                            }
                            break;
                        case 6: // Zipcode
                            if (cell.getCellType() == CellType.STRING) {
                                po.setZipcode(cell.getStringCellValue());
                                logger.info("Found Zipcode: {}", po.getZipcode());
                            }
                            break;
                        case 7: // Item Number
                            if (cell.getCellType() == CellType.STRING) {
                                po.setPuresourceItemNumber(cell.getStringCellValue());
                                logger.info("Found Item Number: {}", po.getPuresourceItemNumber());
                            }
                            break;
                        case 8: // Quantity
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setQuantity((int) cell.getNumericCellValue());
                                logger.info("Found Quantity: {}", po.getQuantity());
                            }
                            break;
                        case 9: // Amount
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setAmount(cell.getNumericCellValue());
                                logger.info("Found Amount: {}", po.getAmount());
                            }
                            break;
                        case 10: // Quarter
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setQuarter((int) cell.getNumericCellValue());
                                logger.info("Found Quarter: {}", po.getQuarter());
                            }
                            break;
                        default:
                            break;
                    }
                }

                // Increment the actual column index only if not skipping
                if (!columnsToSkip.contains(colIndex)) {
                    actualColumnIndex++;
                }
            }
            sales.add(po);
        }

        workbook.close();
        file.close();

        return sales;
    }

}
