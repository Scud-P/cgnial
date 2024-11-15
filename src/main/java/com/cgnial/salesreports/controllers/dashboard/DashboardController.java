package com.cgnial.salesreports.controllers.dashboard;

import com.cgnial.salesreports.domain.DTO.dashboard.DashboardToDistributorSalesSummaryDTO;
import com.cgnial.salesreports.service.dashboard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/salesToDistributor/summary")
    public ResponseEntity<DashboardToDistributorSalesSummaryDTO> getSummary() {
        DashboardToDistributorSalesSummaryDTO summary = dashboardService.getTodaySummary();
        return ResponseEntity.ok(summary);
    }

    //TODO ALERTE FILL RATE < 95% sur un produit sur un mois
    //TODO DERNIÈRE DATE DE PO POUR CHAQUE DISTRIBUTEUR
    //TODO POST MAPPING POUR MAJ PO MASTER
    //TODO POST MAPPING POUR MAJ VENTES DISTRIBUTEURS

}
