package com.cgnial.salesreports.controllers.dev;

import com.cgnial.salesreports.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/po")
public class PurchaseOrderController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @DeleteMapping("/batchDelete")
    public ResponseEntity<String> deleteAllPurchaseOrders() {
        purchaseOrderService.deleteAllPurchaseOrders();
        return ResponseEntity.ok("POs deleted from DB");
    }

    @PostMapping("/batchSave")
    public ResponseEntity<String> saveAllPurchaseOrders() throws IOException {
        purchaseOrderService.saveAllPurchaseOrders();
        return ResponseEntity.ok("POs saved to DB");
    }
}

