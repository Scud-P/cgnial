package com.cgnial.salesreports.domain.DTO.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDistributorSalesSummaryDTO {
    private double lastYearSales;
    private double thisYearSales;
    private String lastUploadedSale;
}
