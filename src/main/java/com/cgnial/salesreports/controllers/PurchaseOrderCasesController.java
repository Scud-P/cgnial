package com.cgnial.salesreports.controllers;

import com.cgnial.salesreports.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/cases")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseOrderCasesController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/batchSave")
    public ResponseEntity<String> loadAllCasesSales() throws IOException {
        purchaseOrderService.saveAllPurchaseOrderCasesQuantity();
        return ResponseEntity.ok("Case quantities saved to DB");
    }

    @DeleteMapping("/batchDelete")
    public ResponseEntity<String> deleteAllCasesSales() throws IOException {
        purchaseOrderService.deleteAllPurchaseOrderCasesQuantity();
        return ResponseEntity.ok("Case quantities DB cleared");
    }

}
