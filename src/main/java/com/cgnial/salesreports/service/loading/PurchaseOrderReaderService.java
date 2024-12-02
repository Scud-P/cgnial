package com.cgnial.salesreports.service.loading;

import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.domain.PurchaseOrderProduct;
import com.cgnial.salesreports.util.DatesUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PurchaseOrderReaderService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderReaderService.class);

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


    public List<List<?>> readPurchaseOrdersUpdateExcelFile(MultipartFile file) throws IOException {

        List<PurchaseOrderProduct> poProducts = new ArrayList<>();
        List<PurchaseOrder> pos = new ArrayList<>();


        // stop at 27

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Set<Integer> columnsToSkip = new HashSet<>(List.of(2));

            for (Row row : sheet) {
                // Skip the first few rows (header or initial rows)
                if (row.getRowNum() < 3) {
                    continue;
                }

                // Check if the first cell is empty before processing
                Cell firstCell = row.getCell(0);
                if (firstCell == null || firstCell.getCellType() == CellType.BLANK) {
                    // If the first cell is empty, skip processing the row
                    continue;
                }

                // Proceed only if the row is not empty
                if (isRowEmpty(row)) {
                    break;
                }

                // Create new objects to store the data
                PurchaseOrderProduct product = new PurchaseOrderProduct();
                PurchaseOrder purchaseOrder = new PurchaseOrder();

                int actualColumnIndex = 0;

                int firstPassIndex = 27;

                for (int colIndex = 0; colIndex <= firstPassIndex; colIndex++) {
                    if (columnsToSkip.contains(colIndex)) {
                        continue;
                    }

                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        // Only process non-skipped columns
                        switch (actualColumnIndex) {
                            case 0:
                                if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                                    Date dateValue = cell.getDateCellValue();

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // or any format you prefer
                                    String formattedDate = formatter.format(dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                                    product.setPoDate(formattedDate);
                                    purchaseOrder.setPoDate(formattedDate);
                                }
                                break;
                            case 1: // Distributor
                                if (cell.getCellType() == CellType.STRING) {
                                    product.setDistributor(cell.getStringCellValue());
                                    purchaseOrder.setDistributor(cell.getStringCellValue());
                                }
                                break;
                            case 2: // 100
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredQty(qty);
                                }
                                break;
                            case 3: // 102
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredTwoQty(qty);
                                }
                                break;
                            case 4: // 103
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredThreeQty(qty);
                                }
                                break;
                            case 5: // 104
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredFourQty(qty);
                                }
                                break;
                            case 6: // 105
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredFiveQty(qty);
                                }
                                break;
                            case 7: // 108
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredEightQty(qty);
                                }
                                break;
                            case 8: // 110
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredTenQty(qty);
                                }
                                break;
                            case 9: // 111
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredElevenQty(qty);
                                }
                                break;
                            case 10: // 112
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredTwelveQty(qty);
                                }
                                break;
                            case 11: // 113
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredThirteenQty(qty);
                                }
                                break;
                            case 12: // 114
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredFourteenQty(qty);
                                }
                                break;
                            case 13: // 115
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredFifteenQty(qty);
                                }
                                break;
                            case 14: // 117
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredSeventeenQty(qty);
                                }
                                break;
                            case 15: // 125
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredTwentyFiveQty(qty);
                                }
                                break;
                            case 16: // 126
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredTwentySixQty(qty);
                                }
                                break;

                            case 17: // 127
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredTwentySevenQty(qty);
                                }
                                break;
                            case 18: // 128
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredTwentyEightQty(qty);
                                }
                                break;
                            case 19: // 130
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredThirtyQty(qty);
                                }
                                break;

                            case 20: // 131
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setOneHundredThirtyOneQty(qty);
                                }
                                break;
                            case 21: // 200
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setTwoHundredQty(qty);
                                }
                                break;
                            case 22: // 202
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setTwoHundredTwoQty(qty);
                                }
                                break;
                            case 23: // 204
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setTwoHundredFourQty(qty);
                                }
                                break;
                            case 24: // 205
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setTwoHundredFiveQty(qty);
                                }
                                break;
                            case 25: // 225
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int qty = (int) cell.getNumericCellValue();
                                    product.setTwoHundredTwentyFiveQty(qty);
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
                poProducts.add(product);
                pos.add(purchaseOrder);
            }

            Sheet secondSheet = workbook.getSheetAt(1);

            int columnIndex = 3; // The column index to extract the amount

            for (Row row : secondSheet) {
                // Skip the header or initial rows
                if (row == null || row.getRowNum() < 1) {
                    continue;
                }

                // Check if the row is valid and not empty
                if (isRowEmpty(row)) {
                    logger.debug("Row {} is empty. Stopping further processing.", row.getRowNum());
                    break;
                }

                // Ensure the first cell is not blank
                Cell firstCell = row.getCell(0);
                if (firstCell == null || firstCell.getCellType() == CellType.BLANK) {
                    logger.debug("Skipping row {} as the first cell is empty or blank.", row.getRowNum());
                    continue;
                }

                // Calculate the corresponding index in the `pos` list
                int posIndex = row.getRowNum() - 1;
                if (posIndex < 0 || posIndex >= pos.size()) {
                    logger.error("Row index {} is out of bounds for the 'pos' list (size: {}).", posIndex, pos.size());
                    continue;
                }

                // Get the PurchaseOrder object from the `pos` list
                PurchaseOrder po = pos.get(posIndex);

                // Read the value from the specified column
                Cell cell = row.getCell(columnIndex);
                if (cell == null || cell.getCellType() != CellType.NUMERIC) {
                    logger.debug("Skipping row {} as the cell at column {} is either null or not numeric.", row.getRowNum(), columnIndex);
                    continue;
                }

                // Set the amount in the PurchaseOrder
                double amount = cell.getNumericCellValue();
                po.setAmount(amount);
                logger.info("Set amount for PO at row {}: {}", row.getRowNum(), amount);
            }

        } catch (Exception e) {
            logger.error("Error reading the Excel file: {}", e.getMessage());
            throw new IOException("Failed to process the uploaded file.", e);
        }
        return List.of(poProducts, pos);
    }
}
