package com.cgnial.salesreports.domain.DTO.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardToDistributorSalesSummaryDTO {

    private LocalDate fromDate;
    private LocalDate toDate;
    private double unfiLYSales;
    private double unfiYTDSales;
    private double satauLYSales;
    private double satauYTDSales;
    private double puresourceLYSales;
    private double puresourceYTDSales;
    private double avrilLYSales;
    private double avrilYTDSales;

}
