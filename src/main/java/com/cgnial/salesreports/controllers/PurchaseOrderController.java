package com.cgnial.salesreports.controllers;

import com.cgnial.salesreports.domain.DTO.PurchaseOrderDTO;
import com.cgnial.salesreports.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
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

    @GetMapping("/list")
    public String getAllPurchaseOrders(Model model) {
        List<PurchaseOrderDTO> purchaseOrderDTOs = purchaseOrderService.findAllPurchaseOrders();

        logger.info("Controller found {} POs to display", purchaseOrderDTOs.size());


        for(PurchaseOrderDTO purchaseOrderDTO : purchaseOrderDTOs) {
            logger.info(String.valueOf(purchaseOrderDTO.getId()));
        }

        model.addAttribute("pos", purchaseOrderDTOs);
        return "po/list";
    }

}
