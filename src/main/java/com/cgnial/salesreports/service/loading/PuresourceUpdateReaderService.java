package com.cgnial.salesreports.service.loading;

import com.cgnial.salesreports.domain.parameter.distributorLoading.PuresourcePOSParameter;
import com.cgnial.salesreports.util.DatesUtil;
import com.cgnial.salesreports.util.ExcelWriter;
import com.cgnial.salesreports.util.HtmlTableParser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.*;

@Service
public class PuresourceUpdateReaderService {

    private static final Logger logger = LoggerFactory.getLogger(SatauUpdateReaderService.class);

    @Autowired
    private DatesUtil datesUtil;

    @Autowired
    private HtmlTableParser parser;

    @Autowired
    private ExcelWriter writer;

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

    public void convertHtmlToExcel(String htmlFilePath, String outputExcelPath) throws Exception {
        List<List<String>> tableData = HtmlTableParser.parseHtmlTable(htmlFilePath);
        ExcelWriter.writeToExcel(tableData, outputExcelPath);
    }


    public List<PuresourcePOSParameter> readPuresourcePOSParameters(int month, int year) throws Exception {

        int newMonth;
        int newYear;

        if(month == 12) {
            newYear = year+1;
            newMonth = 1;

        } else {
            newYear = year;
            newMonth = month+1;
        }

        String htmlLocation = "src/main/resources/excels/Puresource Vendor Sales Report OCT-2024 for Item Code SOL.xls";

        String fileLocation = "src/main/resources/excels/Puresource Vendor Sales Report OCT-2024 for Item Code SOL.xlsx";

        convertHtmlToExcel(htmlLocation, fileLocation);

        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        List<PuresourcePOSParameter> sales = new ArrayList<>();

        // Define which columns to skip by their index (mm/yy, customer number, brand code, product description)
        Set<Integer> columnsToSkip = new HashSet<>(List.of(0, 6, 7, 9));

        for (Row row : sheet) {
            // Skip the header row
            if (row.getRowNum() == 0) {
                continue;
            }

            if(row.getCell(0).getStringCellValue().equalsIgnoreCase("")) {
                break;
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
                        case 0: // Customer Name (this is now the 3rd column after skipping)
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
                        case 1: // Address
                            if (cell.getCellType() == CellType.STRING) {
                                po.setAddress(cell.getStringCellValue());
//                                logger.info("Found Address: {}", po.getAddress());
                            }
                            break;
                        case 2: // City
                            if (cell.getCellType() == CellType.STRING) {
                                po.setCity(cell.getStringCellValue());
//                                logger.info("Found City: {}", po.getCity());
                            }
                            break;
                        case 3: // Province
                            if (cell.getCellType() == CellType.STRING) {
                                po.setProvince(cell.getStringCellValue());
//                                logger.info("Found Province: {}", po.getProvince());
                            }
                            break;
                        case 4: // Zipcode
                            if (cell.getCellType() == CellType.STRING) {
                                po.setZipcode(cell.getStringCellValue());
//                                logger.info("Found Zipcode: {}", po.getZipcode());
                            }
                            break;
                        case 5: // Item Number
                            if (cell.getCellType() == CellType.STRING) {
                                po.setPuresourceItemNumber(cell.getStringCellValue());
//                                logger.info("Found Item Number: {}", po.getPuresourceItemNumber());
                            }
                            break;
                        case 6: // Quantity
                            if (cell.getCellType() == CellType.STRING) {
                                int qty = Integer.parseInt(cell.getStringCellValue());
                                po.setQuantity(qty);
//                                logger.info("Found Quantity: {}", po.getQuantity());
                            }
                            break;
                        case 7: // Amount
                            if (cell.getCellType() == CellType.STRING) {
                                double amount = Double.parseDouble(cell.getStringCellValue());
                                po.setAmount(amount);
                            }
                            break;
                        default:
                            break;
                    }
                    po.setMonth(newMonth);
                    po.setYear(newYear);
                    po.setQuarter(datesUtil.determineQuarter(newMonth));
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

        logger.info("New sales found: {}", sales);
        logger.info("{} new sales found", sales.size());
        return sales;
    }

}
