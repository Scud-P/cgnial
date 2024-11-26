package com.cgnial.salesreports.service.loading;

import com.cgnial.salesreports.domain.parameter.distributorLoading.PuresourcePOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.SatauPOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.SatauUpdateParameter;
import com.cgnial.salesreports.util.DatesUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelUpdaterService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUpdaterService.class);

    @Autowired
    private DatesUtil datesUtil;


    private boolean isRowEmpty(Row row) {
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    public List<String> getPuresourceSalesToMerge() {
        return List.of(
                "Well.ca",
                "Well.ca ULC",
                "Well.ca ULC - Calgary"
        );
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
//                    logger.info("Skipping column {} as per configuration.", colIndex);
                    continue;  // Skip this column
                }

                Cell cell = row.getCell(colIndex);
                if (cell != null) {
                    // Only process non-skipped columns
                    switch (actualColumnIndex) {
                        case 0: // Year
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setYear((int) cell.getNumericCellValue());
//                                logger.info("Found Year: {}", po.getYear());
                            }
                            break;
                        case 1: // Month
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setMonth((int) cell.getNumericCellValue());
//                                logger.info("Found Month: {}", po.getMonth());
                            }
                            break;
                        case 2: // Customer Name (this is now the 3rd column after skipping)
                            if (cell.getCellType() == CellType.STRING) {

                                List<String> salesToMerge = getPuresourceSalesToMerge();
                                if (salesToMerge.contains(cell.getStringCellValue())) {
                                    String mergedAccountName = "Well.ca";
                                    po.setCustomerName(mergedAccountName);
                                } else {
                                    po.setCustomerName(cell.getStringCellValue());
//                                logger.info("Found Customer Name: {}", po.getCustomerName());
                                }
                            }
                            break;
                        case 3: // Address
                            if (cell.getCellType() == CellType.STRING) {
                                po.setAddress(cell.getStringCellValue());
//                                logger.info("Found Address: {}", po.getAddress());
                            }
                            break;
                        case 4: // City
                            if (cell.getCellType() == CellType.STRING) {
                                po.setCity(cell.getStringCellValue());
//                                logger.info("Found City: {}", po.getCity());
                            }
                            break;
                        case 5: // Province
                            if (cell.getCellType() == CellType.STRING) {
                                po.setProvince(cell.getStringCellValue());
//                                logger.info("Found Province: {}", po.getProvince());
                            }
                            break;
                        case 6: // Zipcode
                            if (cell.getCellType() == CellType.STRING) {
                                po.setZipcode(cell.getStringCellValue());
//                                logger.info("Found Zipcode: {}", po.getZipcode());
                            }
                            break;
                        case 7: // Item Number
                            if (cell.getCellType() == CellType.STRING) {
                                po.setPuresourceItemNumber(cell.getStringCellValue());
//                                logger.info("Found Item Number: {}", po.getPuresourceItemNumber());
                            }
                            break;
                        case 8: // Quantity
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setQuantity((int) cell.getNumericCellValue());
//                                logger.info("Found Quantity: {}", po.getQuantity());
                            }
                            break;
                        case 9: // Amount
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setAmount((double) cell.getNumericCellValue());
//                                logger.info("Found Amount: {}", po.getAmount());
                            }
                            break;
                        case 10: // Quarter
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setQuarter((int) cell.getNumericCellValue());
//                                logger.info("Found Quarter: {}", po.getQuarter());
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


    //TODO put String fileLocation in parameter, add a file picker in frontend and a POST endpoint for it in the backend

    public List<SatauUpdateParameter> readSatauSales() throws IOException {
        String fileLocation = "src/main/resources/excels/MOUL001_YTD_2024_09.xlsx";
        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        List<SatauUpdateParameter> sales = new ArrayList<>();

        // Define which columns to skip by their index
        Set<Integer> columnsToSkip = new HashSet<>(Arrays.asList(2, 3, 4, 7, 9, 10, 11, 20));

        for (Row row : sheet) {
            // Skip the header row
            if (row.getRowNum() == 0) {
                continue;
            }

            if (isRowEmpty(row)) {
                break;
            }

            SatauUpdateParameter po = new SatauUpdateParameter();

            // Track the current index of the cell to process
            int actualColumnIndex = 0;

            for (int colIndex = 0; colIndex <= row.getLastCellNum(); colIndex++) {
                // Check if the current column index is in the skip list
                if (columnsToSkip.contains(colIndex)) {
//                    logger.info("Skipping column for Satau {} as per configuration.", colIndex);
                    continue;  // Skip this column
                }

                Cell cell = row.getCell(colIndex);
                if (cell != null) {
                    // Only process non-skipped columns
                    switch (actualColumnIndex) {
                        case 0:
                            // Year
                            if (cell.getCellType() == CellType.NUMERIC) {
                                logger.info("Cell was of cellType.GENERAL but was directly considered as an NUMERIC value");
                                po.setYear((int) cell.getNumericCellValue());
                            } else if (cell.getCellType() == CellType.STRING) {
                                // If the cell is a string (GENERAL type may read as a string)
                                try {
                                    logger.info("Cell was of cellType.GENERAL but the String was parsed");
                                    po.setYear(Integer.parseInt(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                    // Handle conversion error if it's not a valid number
                                    throw new IOException("Invalid year format in Excel file at row " + row.getRowNum());
                                }
                            }
                            break;

                        case 1: // yearMonth
                            if (cell.getCellType() == CellType.STRING) {
                                po.setCustomerName(cell.getStringCellValue());
//                                logger.info("Found Satau Customer Name: {}", po.getCustomerName());
                            }
                            break;
//                        case 2: // Customer Group
//                            if (cell.getCellType() == CellType.STRING) {
//                                po.setCustomerGroup(cell.getStringCellValue());
////                                logger.info("Found Satau Customer group: {}", po.getCustomerGroup());
//                            }
//                            break;
//                        case 3: // Item Number
//                            if (cell.getCellType() == CellType.STRING) {
//                                po.setSatauItemNumber(cell.getStringCellValue());
////                                logger.info("Found Satau Item number: {}", po.getSatauItemNumber());
//                            }
//                            break;
//                        case 4: // Quantity
//                            if (cell.getCellType() == CellType.NUMERIC) {
//                                po.setQuantity((int) cell.getNumericCellValue());
////                                logger.info("Found Satau Quantity: {}", po.getQuantity());
//                            }
//                            break;
//                        case 5: // Amount
//                            if (cell.getCellType() == CellType.NUMERIC) {
//                                po.setAmount((double)cell.getNumericCellValue());
////                                logger.info("Found Satau Amount: {}", po.getAmount());
//                            }
//                            break;
//                        case 6: // Address
//                            if (cell.getCellType() == CellType.STRING) {
//                                po.setAddress(cell.getStringCellValue());
////                                logger.info("Found Satau Address Number: {}", po.getAddress());
//                            }
//                            break;
//                        case 7: // City
//                            if (cell.getCellType() == CellType.STRING) {
//                                po.setCity(cell.getStringCellValue());
////                                logger.info("Found Satau City: {}", po.getCity());
//                            }
//                            break;
//                        case 8: // Zip
//                            if (cell.getCellType() == CellType.STRING) {
//                                po.setZipcode(cell.getStringCellValue());
////                                logger.info("Found Satau Zipcode: {}", po.getZipcode());
//                            }
//                            break;
//                        case 9: // Province
//                            if (cell.getCellType() == CellType.STRING) {
//                                po.setProvince(cell.getStringCellValue());
////                                logger.info("Found Satau Province: {}", po.getProvince());
//                            }
//                            break;
//                        case 10: //Month
//                            if(cell.getCellType() == CellType.NUMERIC) {
//                                po.setMonth((int)cell.getNumericCellValue());
////                                logger.info("Found Satau Month: {}", po.getProvince());
//                            }
//                        case 11: //Quarter
//                            if(cell.getCellType() == CellType.NUMERIC) {
//                                po.setQuarter((int)cell.getNumericCellValue());
////                                logger.info("Found Satau Quarter: {}", po.getProvince());
//                            }
//                        default:
//                            break;
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

    public List<SatauPOSParameter> readSatauPOSParameters() throws IOException {
        String fileLocation = "src/main/resources/excels/MOUL001_YTD_2024_10.xlsx";
        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        List<SatauPOSParameter> sales = new ArrayList<>();

        // Define which columns to skip by their index
        Set<Integer> columnsToSkip = new HashSet<>(Arrays.asList(2, 3, 6, 8, 9, 10));

        for (Row row : sheet) {
            // Skip the header row
            if (row.getRowNum() == 0) {
                continue;
            }

            if (isRowEmpty(row)) {
                break;
            }

            SatauPOSParameter po = new SatauPOSParameter();

            // Track the current index of the cell to process
            int actualColumnIndex = 0;

            for (int colIndex = 0; colIndex <= row.getLastCellNum(); colIndex++) {
                // Check if the current column index is in the skip list
                if (columnsToSkip.contains(colIndex)) {
//                    logger.info("Skipping column for Satau {} as per configuration.", colIndex);
                    continue;  // Skip this column
                }

                Cell cell = row.getCell(colIndex);
                if (cell != null) {
                    // Only process non-skipped columns
                    switch (actualColumnIndex) {
                        case 1: // Customer Name
                            if (cell.getCellType() == CellType.STRING) {
                                String value = cell.getStringCellValue();
                                logger.info("Cell value: {}", value);
                                String[] parts = value.split("-");
                                int month = Integer.parseInt(parts[1]);
                                int year = Integer.parseInt(parts[0]);
                                int quarter = datesUtil.determineQuarter(month);
                                po.setMonth(month);
                                po.setYear(year);
                                po.setQuarter(quarter);
                            }
                            break;
                        case 2: // Customer Group
                            if (cell.getCellType() == CellType.STRING) {
                                po.setCustomerName(cell.getStringCellValue());
//                                logger.info("Found Satau Customer group: {}", po.getCustomerGroup());
                            }
                            break;
                        case 3: // Customer Group
                            if (cell.getCellType() == CellType.STRING) {
                                po.setCustomerGroup(cell.getStringCellValue());
//                                logger.info("Found Satau Customer group: {}", po.getCustomerGroup());
                            }
                            break;
                        case 4: // Item Number
                            if (cell.getCellType() == CellType.STRING) {
                                po.setSatauItemNumber(cell.getStringCellValue());
                                logger.info("Found Satau Item number: {}", po.getSatauItemNumber());
                            }
                            break;
                        case 5: // Quantity
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setQuantity((int) cell.getNumericCellValue());
                            }
                            break;
                        case 6: // Amount
                            if (cell.getCellType() == CellType.NUMERIC) {
                                po.setAmount((double) cell.getNumericCellValue());
//                                logger.info("Found Satau Amount: {}", po.getAmount());
                            }
                            break;
                        case 7: // Address
                            if (cell.getCellType() == CellType.STRING) {
                                po.setAddress(cell.getStringCellValue());
//                                logger.info("Found Satau Address Number: {}", po.getAddress());
                            }
                            break;
                        case 8: // City
                            if (cell.getCellType() == CellType.STRING) {
                                po.setCity(cell.getStringCellValue());
//                                logger.info("Found Satau City: {}", po.getCity());
                            }
                            break;
                        case 9: // Zip
                            if (cell.getCellType() == CellType.STRING) {
                                po.setZipcode(cell.getStringCellValue());
//                                logger.info("Found Satau Zipcode: {}", po.getZipcode());
                            }
                            break;
                        case 10: // Province
                            if (cell.getCellType() == CellType.STRING) {
                                po.setProvince(cell.getStringCellValue());
//                                logger.info("Found Satau Province: {}", po.getProvince());
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
