package com.cgnial.salesreports.controllers.dev;

import com.cgnial.salesreports.domain.parameter.distributorLoading.PuresourcePOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.SatauPOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.UnfiPOSParameter;
import com.cgnial.salesreports.service.loading.ExcelReaderService;
import com.cgnial.salesreports.service.loading.POSSalesLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pos")
public class POSSalesController {

    @Autowired
    private POSSalesLoaderService posSalesLoaderService;

    @Autowired
    private ExcelReaderService excelReaderService;

    @GetMapping("/resetAutoIncrement")
    public ResponseEntity<String> resetAutoIncrement() {
        posSalesLoaderService.resetAutoIncrement();
        return ResponseEntity.ok("AutoIncrement has been reset");
    }

    @GetMapping("/loadAllPuresourceSales")
    public ResponseEntity<String> loadAllPuresourceSales() throws IOException {
        List<PuresourcePOSParameter> rawSales = excelReaderService.readPuresourcePOSParameters();
        posSalesLoaderService.loadAllPuresourceSales(rawSales);
        return ResponseEntity.ok("Puresource sales loaded to database. " + rawSales.size() + " transactions found");
    }

    @GetMapping("/clearAllPuresourceSales")
    public ResponseEntity<String> clearAllPuresourceSales() {
        posSalesLoaderService.clearAllPuresourceSales();
        return ResponseEntity.ok("Sales database emptied from Puresource Sales");
    }

    @GetMapping("/loadAllSatauSales")
    public ResponseEntity<String> loadAllSatauSales() throws IOException {
        List<SatauPOSParameter> rawSales = excelReaderService.readSatauPOSParameters();
        posSalesLoaderService.loadAllSatauSales(rawSales);
        return ResponseEntity.ok(rawSales.size() + " Satau sales loaded to database");
    }

    @GetMapping("/clearAllSatauSales")
    public ResponseEntity<String> clearAllSatauSales() {
        posSalesLoaderService.clearAllSatauSales();
        return ResponseEntity.ok("Sales database emptied from Satau Sales");
    }

    @GetMapping("/loadAllUnfiSales")
    public ResponseEntity<String> loadAllUnfiSales() throws IOException {
        List<UnfiPOSParameter> rawSales = excelReaderService.readUNFIPOSParameters();
        posSalesLoaderService.loadAllUnfiSales(rawSales);
        return ResponseEntity.ok(rawSales.size() + " UNFI sales loaded to database");
    }

    @GetMapping("/clearAllUnfiSales")
    public ResponseEntity<String> clearAllUnfiSales() {
        posSalesLoaderService.clearAllUnfiSales();
        return ResponseEntity.ok("Sales database emptied from UNFI Sales");
    }

}
