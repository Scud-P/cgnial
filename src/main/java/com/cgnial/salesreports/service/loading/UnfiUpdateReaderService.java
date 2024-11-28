package com.cgnial.salesreports.service.loading;

import com.cgnial.salesreports.domain.parameter.distributorLoading.UnfiPOSParameter;
import com.cgnial.salesreports.util.FileExtensionChanger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class UnfiUpdateReaderService {

    private static final Logger logger = LoggerFactory.getLogger(UnfiUpdateReaderService.class);

    @Autowired
    private FileExtensionChanger fileExtensionChanger;

    private boolean isRowEmpty(Row row) {
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }


    public List<UnfiPOSParameter> readUNFIPOSParameters(MultipartFile file) throws IOException {

        List<UnfiPOSParameter> sales = new ArrayList<>();
        Set<Integer> columnsToSkip = new HashSet<>(Arrays.asList(1, 2, 4, 5, 6, 7, 8, 14, 18, 24, 25, 26, 27, 28));


        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(10);

            for (Row row : sheet) {
                // Skip the header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                if (isRowEmpty(row)) {
                    break;
                }

                UnfiPOSParameter po = new UnfiPOSParameter();

                int actualColumnIndex = 0;

                for (int colIndex = 0; colIndex <= row.getLastCellNum(); colIndex++) {
                    if (columnsToSkip.contains(colIndex)) {
//                        logger.info("Skipping column for UNFI {} as per configuration.", colIndex);
                        continue;
                    }

                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        // Only process non-skipped columns
                        switch (actualColumnIndex) {

                            case 0: //Week
                                if (cell.getCellType() == CellType.STRING) {
                                    int week = Integer.parseInt(cell.getStringCellValue());
                                    po.setWeek(week);
                                    logger.info("Found UNFI Week: {}", po.getWeek());
                                }
                                break;

                            case 1: //Item Number
                                if (cell.getCellType() == CellType.STRING) {
                                    po.setUnfiItemNumber(cell.getStringCellValue());
                                    logger.info("Found UNFI Item number: {}", po.getUnfiItemNumber());
                                }
                                break;

                            case 2: // Customer Name
                                if (cell.getCellType() == CellType.STRING) {
                                    po.setCustomerName(cell.getStringCellValue());
                                    logger.info("Found UNFI Customer Name: {}", po.getCustomerName());
                                }
                                break;
                            case 3: // Address
                                if (cell.getCellType() == CellType.STRING) {
                                    po.setAddress(cell.getStringCellValue());
                                    logger.info("Found UNFI Address Number: {}", po.getAddress());
                                }
                                break;
                            case 4: // City
                                if (cell.getCellType() == CellType.STRING) {
                                    po.setCity(cell.getStringCellValue());
                                    logger.info("Found UNFI City: {}", po.getCity());
                                }
                                break;
                            case 5: // Province
                                if (cell.getCellType() == CellType.STRING) {
                                    po.setProvince(cell.getStringCellValue());
                                    logger.info("Found UNFI Province: {}", po.getProvince());
                                }
                                break;
                            case 6: // Zip
                                if (cell.getCellType() == CellType.STRING) {
                                    po.setZipcode(cell.getStringCellValue());
                                    logger.info("Found UNFI Zipcode: {}", po.getZipcode());
                                }
                                break;
                            case 7: // Zip
                                if (cell.getCellType() == CellType.STRING) {
                                    po.setCustomerGroup(cell.getStringCellValue());
                                    logger.info("Found UNFI Zipcode: {}", po.getCustomerGroup());
                                }
                                break;

                            case 8: //Year
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    po.setYear((int) cell.getNumericCellValue());
                                    logger.info("Found UNFI Year: {}", po.getYear());
                                }
                                break;
                            case 9: //Month
                                if (cell.getCellType() == CellType.STRING) {
                                    po.setMonth(cell.getStringCellValue());
                                    logger.info("Found UNFI Month: {}", po.getMonth());
                                }
                                break;
                            case 10: // Quantity
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    po.setQuantity((int) cell.getNumericCellValue());
                                    logger.info("Found UNFI Quantity: {}", po.getQuantity());
                                }
                                break;
                            case 11: // Order Quantity
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    po.setOrderQuantity((int) cell.getNumericCellValue());
                                    logger.info("Found UNFI Order Quantity: {}", po.getOrderQuantity());
                                }
                                break;
                            case 12: // Amount
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    po.setAmount((double) cell.getNumericCellValue());
                                    logger.info("Found UNFI Amount: {}", po.getAmount());
                                }
                                break;
                            case 13: // Order Amount
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    po.setOrderAmount((double) cell.getNumericCellValue());
                                    logger.info("Found UNFI Order Amount: {}", po.getOrderAmount());
                                }
                                break;
                            case 14: // MCB amount
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    po.setMcbAmount((double) cell.getNumericCellValue());
                                    logger.info("Found UNFI MCB Amount: {}", po.getMcbAmount());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    if (!columnsToSkip.contains(colIndex)) {
                        actualColumnIndex++;
                    }
                }
                sales.add(po);
            }
        }
        return sales;
    }
}
