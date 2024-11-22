package com.cgnial.salesreports.controllers.dashboard;

import com.cgnial.salesreports.domain.DTO.dashboard.*;
import com.cgnial.salesreports.service.dashboard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/salesToDistributor/summary")
    public ResponseEntity<DashboardToDistributorSalesSummaryDTO> getSummary() {
        DashboardToDistributorSalesSummaryDTO summary = dashboardService.getTodaySummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/distributorSales/satau")
    public ResponseEntity<DashboardDistributorSalesSummaryDTO> getSatauSales() {
        DashboardDistributorSalesSummaryDTO summary = dashboardService.getDistributorSummary("Satau");
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/distributorSales/unfi")
    public ResponseEntity<DashboardDistributorSalesSummaryDTO> getUnfiSales() {
        DashboardDistributorSalesSummaryDTO summary = dashboardService.getDistributorSummary("Unfi");
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/distributorSales/puresource")
    public ResponseEntity<DashboardDistributorSalesSummaryDTO> getPuresourceSales() {
        DashboardDistributorSalesSummaryDTO summary = dashboardService.getDistributorSummary("Puresource");
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/salesToDistributor/lastPos")
    public ResponseEntity<LastPOsByDistributorDTO> getLastPoDates() {
        LastPOsByDistributorDTO lastPos = dashboardService.getLastPODates();
        return ResponseEntity.ok(lastPos);
    }

    @GetMapping("/distributorSales/unfi/fillRates")
    public ResponseEntity<List<DashboardDetailedFillRatesDTO>> getUnfiRecentFillRates() {
        List<DashboardDetailedFillRatesDTO> recentFillRates = dashboardService.getRecentFillRatesAlerts();
        return ResponseEntity.ok(recentFillRates);
    }


    //TODO ALERTE FILL RATE < 95% sur un produit sur un mois
    //TODO POST MAPPING POUR MAJ PO MASTER
    //TODO POST MAPPING POUR MAJ VENTES DISTRIBUTEURS

}
