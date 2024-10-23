package com.cgnial.salesreports.controllers;

import com.cgnial.salesreports.domain.DTO.SalesByDistributorByYearDTO;
import com.cgnial.salesreports.domain.DTO.SalesPerDistributorDTO;
import com.cgnial.salesreports.service.DistributorSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/distributorSales")
@CrossOrigin(origins = "http://localhost:3000")
public class DistributorSalesController {

    @Autowired
    private DistributorSalesService distributorSalesService;

    @GetMapping("/byDistributorByYear")
    public ResponseEntity<List<SalesByDistributorByYearDTO>> salesByDistributorByYear() {
        List<SalesByDistributorByYearDTO> sales = distributorSalesService.getSalesByDistributorByYear();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/byDistributorByYearByQuarter")
    public ResponseEntity<List<SalesPerDistributorDTO>> salesByDistributorByYearByQuarter() {
        List<SalesPerDistributorDTO> sales = distributorSalesService.getSalesByDistributorByYearAndQuarter();
        return ResponseEntity.ok(sales);
    }

}
