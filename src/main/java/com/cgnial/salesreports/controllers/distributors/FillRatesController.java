package com.cgnial.salesreports.controllers.distributors;

import com.cgnial.salesreports.domain.DTO.fillRates.YearlyFillRatesBySkuDTO;
import com.cgnial.salesreports.service.fillRates.FillRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/fillRates")
public class FillRatesController {

    @Autowired
    private FillRatesService fillRatesService;

    @GetMapping("/unfi")
    public ResponseEntity<List<YearlyFillRatesBySkuDTO>> getMonthlyFillRatesBySKU() {
        List<YearlyFillRatesBySkuDTO> allFillRates = fillRatesService.getYearlyFillRatesBySKU();
        return ResponseEntity.ok(allFillRates);
    }

    @GetMapping("/currentMonth")
    public ResponseEntity<List<YearlyFillRatesBySkuDTO>> getCurrentMonthFillRatesBySKU() {
        List<YearlyFillRatesBySkuDTO> fillRates = fillRatesService.getFillRatesForCurrentMonthReport();
        return ResponseEntity.ok(fillRates);
    }

}
