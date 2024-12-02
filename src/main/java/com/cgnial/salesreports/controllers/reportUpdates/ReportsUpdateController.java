package com.cgnial.salesreports.controllers.reportUpdates;


import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.domain.PurchaseOrderProduct;
import com.cgnial.salesreports.service.updating.DistributorSalesUpdaterService;
import com.cgnial.salesreports.service.updating.PurchaseOrderUpdaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/update")
@RestController
public class ReportsUpdateController {

    @Autowired
    private DistributorSalesUpdaterService salesService;

    @Autowired
    private PurchaseOrderUpdaterService poService;

    private static final Logger logger = LoggerFactory.getLogger(ReportsUpdateController.class);


    @PostMapping("/satauSales")
    public ResponseEntity<List<POSSale>> updateSatauSales(@RequestParam("file")MultipartFile file) throws IOException {
        List<POSSale> satauSales = salesService.loadNewSatauSales(file);
        logger.info("Endpoint received a post request");
        return ResponseEntity.ok(satauSales);
    }

    @PostMapping("/unfiSales")
    public ResponseEntity<List<POSSale>> updateUnfiSales(@RequestParam("file")MultipartFile file) throws IOException {
        List<POSSale> unfiSales = salesService.loadNewUnfiSales(file);
        return ResponseEntity.ok(unfiSales);
    }

    @PostMapping("/puresourceSales")
    public ResponseEntity<List<POSSale>> uploadPuresourceSales(@RequestParam("file")MultipartFile file) throws Exception {
        List<POSSale> puresourceSales = salesService.loadNewPuresourceSales(file);
        return ResponseEntity.ok(puresourceSales);
    }

    //TODO RENAME AND MOVE THE METHOD BELOW

    @Transactional
    @PostMapping("/poMasterSalesToRemove")
    public ResponseEntity<List<List<?>>> uploadPurchaseOrders(@RequestParam("file")MultipartFile file) throws Exception {
        List<PurchaseOrder> uploadedPurchaseOrders = poService.uploadPurchaseOrders(file);
        List<PurchaseOrderProduct> uploadPurchaseOrderProducts = poService.uploadPurchaseOrderProducts(file);
        return ResponseEntity.ok(List.of(uploadedPurchaseOrders, uploadPurchaseOrderProducts));
    }

    @PostMapping("/poMasterSales")
    public ResponseEntity<String> updatePurchaseOrders(@RequestParam("file") MultipartFile file) throws Exception {

        Thread.sleep(2000);

        List<PurchaseOrder> updatedPurchaseOrder = poService.updatePurchaseOrders(file);
        Thread.sleep(2000);
        logger.info("List of updated PurchaseOrders: {}", updatedPurchaseOrder); // Log the actual list


        List<PurchaseOrderProduct> updatedPurchaseOrderProducts = poService.updatePurchaseOrderProducts(file);
        Thread.sleep(2000);
        logger.info("List of updated PurchaseOrderProducts: {}", updatedPurchaseOrderProducts);


        return ResponseEntity.ok(updatedPurchaseOrder.toString() + updatedPurchaseOrderProducts.toString());
    }
}
