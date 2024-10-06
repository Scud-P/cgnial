package com.cgnial.salesreports.controllers;

import com.cgnial.salesreports.domain.parameter.PuresourcePOSParameter;
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

    @GetMapping("/loadAllPuresourceSales")
    public ResponseEntity<String> loadAllPOSSales() throws IOException {
        List<PuresourcePOSParameter> rawSales = excelReaderService.readPuresourcePOSParameters();
        posSalesService.loadAllPuresourceSales(rawSales);
        return ResponseEntity.ok("Sales loaded to database");
    }

    @GetMapping("/clearAllPuresourceSales")
    public ResponseEntity<String> clearAllPOSSales() {
        posSalesService.clearAllPuresourceSales();
        return ResponseEntity.ok("Sales database emptied from Puresource Sales");
    }

}
