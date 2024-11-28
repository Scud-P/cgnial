package com.cgnial.salesreports.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class ExcelWriter {
    public static byte[] writeToExcel(List<List<String>> tableData) throws Exception {
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

        // Write to a ByteArrayOutputStream
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            workbook.close();
            return bos.toByteArray(); // Return the Excel file as a byte array
        }
    }
}
