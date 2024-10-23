package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.PurchaseOrderProduct;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductQuantityReaderService {

    private static final Logger logger = LoggerFactory.getLogger(ProductQuantityReaderService.class);


    public List<PurchaseOrderProduct> readCaseQuantities() throws IOException {
        String fileLocation = "src/main/resources/excels/pomaster.xlsx";
        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(1);

        List<PurchaseOrderProduct> pos = new ArrayList<>();

        for(Row row : sheet) {

            if (row.getRowNum() == 0) {
                continue;
            }

            if (isRowEmpty(row)) {
                break;
            }

            PurchaseOrderProduct po = new PurchaseOrderProduct();
            if(row.getCell(0) != null) {
                po.setPoDate(row.getCell(0).getStringCellValue());
            }
            if(row.getCell(1) != null) {
                po.setDistributor(row.getCell(1).getStringCellValue());
            }
            if(row.getCell(2) != null) {
                po.setOneHundredQty((int)row.getCell(2).getNumericCellValue());
            }
            if(row.getCell(3) != null) {
                po.setOneHundredTwoQty((int)row.getCell(3).getNumericCellValue());
            }
            if(row.getCell(4) != null) {
                po.setOneHundredThreeQty((int)row.getCell(4).getNumericCellValue());
            }
            if(row.getCell(5) != null) {
                po.setOneHundredFourQty((int)row.getCell(5).getNumericCellValue());
            }
            if(row.getCell(6) != null) {
                po.setOneHundredFiveQty((int)row.getCell(6).getNumericCellValue());
            }
            if(row.getCell(7) != null) {
                po.setOneHundredEightQty((int)row.getCell(7).getNumericCellValue());
            }
            if(row.getCell(8) != null) {
                po.setOneHundredTenQty((int)row.getCell(8).getNumericCellValue());
            }
            if(row.getCell(9) != null) {
                po.setOneHundredElevenQty((int)row.getCell(9).getNumericCellValue());
            }
            if(row.getCell(10) != null) {
                po.setOneHundredTwelveQty((int)row.getCell(10).getNumericCellValue());
            }
            if(row.getCell(11) != null) {
                po.setOneHundredThirteenQty((int)row.getCell(11).getNumericCellValue());
            }
            if(row.getCell(12) != null) {
                po.setOneHundredFourteenQty((int)row.getCell(12).getNumericCellValue());
            }
            if(row.getCell(13) != null) {
                po.setOneHundredFifteenQty((int)row.getCell(13).getNumericCellValue());
            }
            if(row.getCell(14) != null) {
                po.setOneHundredSeventeenQty((int)row.getCell(14).getNumericCellValue());
            }
            if(row.getCell(15) != null) {
                po.setOneHundredTwentyFiveQty((int)row.getCell(15).getNumericCellValue());
            }
            if(row.getCell(16) != null) {
                po.setOneHundredTwentySixQty((int)row.getCell(16).getNumericCellValue());
            }
            if(row.getCell(16) != null) {
                po.setOneHundredTwentySevenQty((int)row.getCell(16).getNumericCellValue());
            }
            if(row.getCell(17) != null) {
                po.setOneHundredTwentyEightQty((int)row.getCell(17).getNumericCellValue());
            }
            if(row.getCell(18) != null) {
                po.setOneHundredThirtyQty((int)row.getCell(18).getNumericCellValue());
            }
            if(row.getCell(19) != null) {
                po.setOneHundredThirtyOneQty((int)row.getCell(19).getNumericCellValue());
            }
            if(row.getCell(20) != null) {
                po.setTwoHundredQty((int)row.getCell(20).getNumericCellValue());
            }
            if(row.getCell(21) != null) {
                po.setTwoHundredTwoQty((int)row.getCell(21).getNumericCellValue());
            }
            if(row.getCell(22) != null) {
                po.setTwoHundredFourQty((int)row.getCell(22).getNumericCellValue());
            }
            if(row.getCell(23) != null) {
                po.setTwoHundredFiveQty((int)row.getCell(23).getNumericCellValue());
            }
            if(row.getCell(24) != null) {
                po.setTwoHundredTwentyFiveQty((int)row.getCell(24).getNumericCellValue());
            }
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

}
