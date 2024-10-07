package com.cgnial.salesreports.controllers;

import com.cgnial.salesreports.domain.parameter.PuresourcePOSParameter;
import com.cgnial.salesreports.domain.parameter.SatauPOSParameter;
import com.cgnial.salesreports.service.ExcelReaderService;
import com.cgnial.salesreports.service.POSSalesService;
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
    private POSSalesService posSalesService;

    @Autowired
    private ExcelReaderService excelReaderService;

    @GetMapping("/resetAutoIncrement")
    public ResponseEntity<String> resetAutoIncrement() {
        posSalesService.resetAutoIncrement();
        return ResponseEntity.ok("AutoIncrement has been reset");
    }

    @GetMapping("/loadAllPuresourceSales")
    public ResponseEntity<String> loadAllPuresourceSales() throws IOException {
        List<PuresourcePOSParameter> rawSales = excelReaderService.readPuresourcePOSParameters();
        posSalesService.loadAllPuresourceSales(rawSales);
        return ResponseEntity.ok("Puresource sales loaded to database");
    }

    @GetMapping("/clearAllPuresourceSales")
    public ResponseEntity<String> clearAllPuresourceSales() {
        posSalesService.clearAllPuresourceSales();
        return ResponseEntity.ok("Sales database emptied from Puresource Sales");
    }

    @GetMapping("/loadAllSatauSales")
    public ResponseEntity<String> loadAllSatauSales() throws IOException {
        List<SatauPOSParameter> rawSales = excelReaderService.readSatauPOSParameters();
        posSalesService.loadAllSatauSales(rawSales);
        return ResponseEntity.ok(rawSales.size() + " Satau sales loaded to database");
    }

    @GetMapping("/clearAllSatauSales")
    public ResponseEntity<String> clearAllSatauSales() {
        posSalesService.clearAllPuresourceSales();
        return ResponseEntity.ok("Sales database emptied from Satau Sales");
    }

}
