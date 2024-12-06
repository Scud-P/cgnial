package com.cgnial.salesreports.controllers.dev;

import com.cgnial.salesreports.service.loading.PurchaseOrderLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/po")
public class PurchaseOrderController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private PurchaseOrderLoaderService purchaseOrderService;

    @DeleteMapping("/batchDelete")
    public ResponseEntity<String> deleteAllPurchaseOrders() {
        purchaseOrderService.deleteAllPurchaseOrders();
        return ResponseEntity.ok("POs deleted from DB");
    }

    @PostMapping("/batchSave")
    public ResponseEntity<String> saveAllPurchaseOrders(MultipartFile file) throws IOException {
        purchaseOrderService.saveAllPurchaseOrders(file);
        return ResponseEntity.ok("POs saved to DB");
    }

    @PostMapping("/initialSave")
    public ResponseEntity<String> saveInitialPurchaseOrders() throws IOException {
        purchaseOrderService.saveInitialPos();
        return ResponseEntity.ok("POs saved to DB");
    }

}

