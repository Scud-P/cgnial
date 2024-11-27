package com.cgnial.salesreports.service.loading;

import com.cgnial.salesreports.domain.parameter.distributorLoading.SatauPOSParameter;
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
public class SatauUpdateReaderService {

    private static final Logger logger = LoggerFactory.getLogger(SatauUpdateReaderService.class);

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


    //TODO put String fileLocation in parameter, add a file picker in frontend and a POST endpoint for it in the backend

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
