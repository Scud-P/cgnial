package com.cgnial.salesreports.controllers.toDistributors;

import com.cgnial.salesreports.domain.DTO.toDistributors.SalesByDistributorByYearDTO;
import com.cgnial.salesreports.domain.DTO.toDistributors.SalesPerDistributorDTO;
import com.cgnial.salesreports.service.toDistributors.SalesToDistributorsService;
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
    private SalesToDistributorsService salesToDistributorsService;

    @GetMapping("/byDistributorByYear")
    public ResponseEntity<List<SalesByDistributorByYearDTO>> salesByDistributorByYear() {
        List<SalesByDistributorByYearDTO> sales = salesToDistributorsService.getSalesByDistributorByYear();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/byDistributorByYearByQuarter")
    public ResponseEntity<List<SalesPerDistributorDTO>> salesByDistributorByYearByQuarter() {
        List<SalesPerDistributorDTO> sales = salesToDistributorsService.getSalesByDistributorByYearAndQuarter();
        return ResponseEntity.ok(sales);
    }

}
