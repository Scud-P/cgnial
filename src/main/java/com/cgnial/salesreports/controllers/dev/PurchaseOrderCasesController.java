package com.cgnial.salesreports.controllers.dev;

import com.cgnial.salesreports.service.loading.PurchaseOrderLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cases")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseOrderCasesController {

    @Autowired
    private PurchaseOrderLoaderService purchaseOrderService;

    @PostMapping("/batchSave")
    public ResponseEntity<String> loadAllCasesSales(MultipartFile file) throws IOException {
        purchaseOrderService.saveAllPurchaseOrderCasesQuantity(file);
        return ResponseEntity.ok("Case quantities saved to DB");
    }

    @DeleteMapping("/batchDelete")
    public ResponseEntity<String> deleteAllCasesSales() throws IOException {
        purchaseOrderService.deleteAllPurchaseOrderCasesQuantity();
        return ResponseEntity.ok("Case quantities DB cleared");
    }

    @PostMapping("/initialSave")
    public ResponseEntity<String> loadAllCasesSales() throws IOException {
        purchaseOrderService.saveInitialPoQuantities();
        return ResponseEntity.ok("Case quantities saved to DB");
    }

}
