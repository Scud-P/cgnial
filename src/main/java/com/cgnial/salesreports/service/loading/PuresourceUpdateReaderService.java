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
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public List<List<String>> parseAndWriteHtmlToExcel(MultipartFile htmlFile) throws Exception {
        // Parse the HTML file
        InputStream inputStream = htmlFile.getInputStream();
        List<List<String>> tableData = HtmlTableParser.parseHtmlTable(inputStream);

        // Write the table data to Excel
        byte[] excelData = ExcelWriter.writeToExcel(tableData);

        // You can now either:
        // 1. Save the `excelData` to a file if needed
        // 2. Process the `excelData` directly (e.g., send to another service)

        return tableData; // Returning tableData for further processing
    }


    public List<PuresourcePOSParameter> readPuresourcePOSParameters(MultipartFile htmlFile, int month, int year) throws Exception {

        int newMonth = (month == 12) ? 1 : month + 1;
        int newYear = (month == 12) ? year + 1 : year;

        // Parse HTML and convert it to Excel in memory
        List<List<String>> tableData = HtmlTableParser.parseHtmlTable(htmlFile.getInputStream());
        byte[] excelData = ExcelWriter.writeToExcel(tableData);

        // Create a Workbook from the in-memory Excel data
        try (InputStream excelStream = new ByteArrayInputStream(excelData);
             Workbook workbook = new XSSFWorkbook(excelStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<PuresourcePOSParameter> sales = new ArrayList<>();

            // Define which columns to skip by their index
            Set<Integer> columnsToSkip = new HashSet<>(List.of(0, 6, 7, 9));

            for (Row row : sheet) {
                // Skip the header row
                if (row.getRowNum() == 0) continue;

                if (isRowEmpty(row)) break;

                PuresourcePOSParameter po = new PuresourcePOSParameter();

                int actualColumnIndex = 0;
                for (int colIndex = 0; colIndex <= row.getLastCellNum(); colIndex++) {
                    if (columnsToSkip.contains(colIndex)) continue;

                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        switch (actualColumnIndex) {
                            case 0: // Customer Name
                                if (cell.getCellType() == CellType.STRING) {
                                    List<String> salesToMerge = getPuresourceSalesToMerge();
                                    String customerName = cell.getStringCellValue();
                                    po.setCustomerName(salesToMerge.contains(customerName) ? "Well.ca" : customerName);
                                }
                                break;
                            case 1: // Address
                                if (cell.getCellType() == CellType.STRING) po.setAddress(cell.getStringCellValue());
                                break;
                            case 2: // City
                                if (cell.getCellType() == CellType.STRING) po.setCity(cell.getStringCellValue());
                                break;
                            case 3: // Province
                                if (cell.getCellType() == CellType.STRING) po.setProvince(cell.getStringCellValue());
                                break;
                            case 4: // Zipcode
                                if (cell.getCellType() == CellType.STRING) po.setZipcode(cell.getStringCellValue());
                                break;
                            case 5: // Item Number
                                if (cell.getCellType() == CellType.STRING) po.setPuresourceItemNumber(cell.getStringCellValue());
                                break;
                            case 6: // Quantity
                                if(cell.getCellType() == CellType.STRING && cell.getStringCellValue().equalsIgnoreCase("")) break;
                                if (cell.getCellType() == CellType.STRING) po.setQuantity(Integer.parseInt(cell.getStringCellValue()));
                                break;
                            case 7: // Amount
                                if(cell.getCellType() == CellType.STRING && cell.getStringCellValue().equalsIgnoreCase("")) break;
                                if (cell.getCellType() == CellType.STRING) po.setAmount(Double.parseDouble(cell.getStringCellValue()));
                                break;
                            default:
                                break;
                        }
                    }

                    if (!columnsToSkip.contains(colIndex)) actualColumnIndex++;
                }

                po.setMonth(newMonth);
                po.setYear(newYear);
                po.setQuarter(datesUtil.determineQuarter(newMonth));

                sales.add(po);
            }
            return sales;
        }
    }
}
