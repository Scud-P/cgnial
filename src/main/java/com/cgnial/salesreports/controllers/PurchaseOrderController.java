package com.cgnial.salesreports.controllers;

import com.cgnial.salesreports.domain.DTO.DistributorSalesDTO;
import com.cgnial.salesreports.domain.DTO.PurchaseOrderDTO;
import com.cgnial.salesreports.domain.DTO.SalesPerDistributorPerYearDTO;
import com.cgnial.salesreports.domain.DTO.YearlySalesByDistributorDTO;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("pos", purchaseOrderDTOs);
        return "po/list";
    }

    @GetMapping("/dashboard")
    public String getYearlySalesByDistributor(Model model) {
        List<YearlySalesByDistributorDTO> yearlySalesByDistributorDTOS = purchaseOrderService.getYearlySalesByDistributorDTOS();
        model.addAttribute("yearlySalesByDistributor", yearlySalesByDistributorDTOS);
        return "po/dashboard";
    }

    @GetMapping("/salesByDistributorByYear")
    public String getSalesByDistributorByYearGraph(Model model) {
        List<DistributorSalesDTO> salesData = purchaseOrderService.getSalesData();
        model.addAttribute("salesMap", salesData);
        return "po/salesByDistributorByYear";
    }

//    @GetMapping("/salesByDistributorByYear")
//    public String getSalesByQuarterByDistributorByYearGraph(Model model) {
//
//        //TODO
//        Map<String, Map<Integer, Double>> salesMap = purchaseOrderService.getSalesByQuarterByDistributorByYear();
//        model.addAttribute("salesMap", salesMap);
//        return "po/salesByDistributorByYear";
//    }

}