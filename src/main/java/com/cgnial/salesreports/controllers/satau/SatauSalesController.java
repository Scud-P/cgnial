package com.cgnial.salesreports.controllers.satau;

import com.cgnial.salesreports.domain.DTO.cases.YearlySatauSalesDTO;
import com.cgnial.salesreports.service.distributorsales.SatauSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/satau")
public class SatauSalesController {

    @Autowired
    private SatauSalesService satauSalesService;

    @GetMapping("/byQuarter")
    public ResponseEntity<List<YearlySatauSalesDTO>> getSatauQuarterlySales() {
        List<YearlySatauSalesDTO> allSatauSales = satauSalesService.getSatauQuarterlySales();
        return ResponseEntity.ok(allSatauSales);
    }

    //TODO 10 meilleurs comptes par an par quarter


}
