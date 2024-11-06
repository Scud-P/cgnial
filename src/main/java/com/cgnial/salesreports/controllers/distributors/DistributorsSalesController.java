package com.cgnial.salesreports.controllers.distributors;

import com.cgnial.salesreports.domain.DTO.distributorSales.YearlyDistributorSalesDTO;
import com.cgnial.salesreports.domain.DTO.distributorSalesBySKU.YearlySalesBySKUDTO;
import com.cgnial.salesreports.service.distributorSales.DistributorsSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/distributorSales")
public class DistributorsSalesController {

    @Autowired
    private DistributorsSalesService distributorsSalesService;

    @GetMapping("/satau/byQuarter")
    public ResponseEntity<List<YearlyDistributorSalesDTO>> getSatauQuarterlySales() {
        List<YearlyDistributorSalesDTO> allSatauSales = distributorsSalesService.getDistributorQuarterlySales("Satau");
        return ResponseEntity.ok(allSatauSales);
    }

    @GetMapping("/unfi/byQuarter")
    public ResponseEntity<List<YearlyDistributorSalesDTO>> getUnfiQuarterlySales() {
        List<YearlyDistributorSalesDTO> allUnfiSales = distributorsSalesService.getDistributorQuarterlySales("Unfi");
        return ResponseEntity.ok(allUnfiSales);
    }

    @GetMapping("/puresource/byQuarter")
    public ResponseEntity<List<YearlyDistributorSalesDTO>> getPuresourceQuarterlySales() {
        List<YearlyDistributorSalesDTO> allPuresourceSales = distributorsSalesService.getDistributorQuarterlySales("Puresource");
        return ResponseEntity.ok(allPuresourceSales);
    }

    @GetMapping("/satau/bySKU")
    public ResponseEntity<List<YearlySalesBySKUDTO>> getSatauQuarterlySalesBySKU() {
        List<YearlySalesBySKUDTO> allSatauSales = distributorsSalesService.getDistributorQuarterlySalesBySKU("Satau");
        return ResponseEntity.ok(allSatauSales);
    }

    @GetMapping("/unfi/bySKU")
    public ResponseEntity<List<YearlySalesBySKUDTO>> getUnfiQuarterlySalesBySKU() {
        List<YearlySalesBySKUDTO> allUnfiSales = distributorsSalesService.getDistributorQuarterlySalesBySKU("Unfi");
        return ResponseEntity.ok(allUnfiSales);
    }

    @GetMapping("/puresource/bySKU")
    public ResponseEntity<List<YearlySalesBySKUDTO>> getPuresourceQuarterlySalesBySKU() {
        List<YearlySalesBySKUDTO> allPuresourceSales = distributorsSalesService.getDistributorQuarterlySalesBySKU("Puresource");
        return ResponseEntity.ok(allPuresourceSales);
    }


    //TODO 10 meilleurs comptes par an par quarter
}
