package com.cgnial.salesreports.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;

@Service
public class ExcelWriter {
    public static void writeToExcel(List<List<String>> tableData, String outputFilePath) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("HTML Table");

        for (int i = 0; i < tableData.size(); i++) {
            Row row = sheet.createRow(i);
            List<String> rowData = tableData.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(rowData.get(j));
            }
        }

        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            workbook.write(fos);
        }

        workbook.close();
        System.out.println("Excel file created: " + outputFilePath);
    }
}
