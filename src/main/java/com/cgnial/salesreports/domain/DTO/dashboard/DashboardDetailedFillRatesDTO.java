package com.cgnial.salesreports.domain.DTO.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDetailedFillRatesDTO {

    private int itemNumber;
    private int currentMonthQuantity;
    private int currentMonthOrderQuantity;
    private int previousMonthQuantity;
    private int previousMonthOrderQuantity;
    private double currentMonthFillRate;
    private double previousMonthFillRate;

    public DashboardDetailedFillRatesDTO(DashboardFillRateDTO dashboardFillRateDTO) {
        this.itemNumber = dashboardFillRateDTO.getItemNumber();
        this.currentMonthQuantity = dashboardFillRateDTO.getCurrentMonthQuantity();
        this.currentMonthOrderQuantity = dashboardFillRateDTO.getCurrentMonthOrderQuantity();
        this.previousMonthQuantity = dashboardFillRateDTO.getPreviousMonthQuantity();
        this.previousMonthOrderQuantity = dashboardFillRateDTO.getPreviousMonthOrderQuantity();
        this.currentMonthFillRate = calculateFillRate(dashboardFillRateDTO.getCurrentMonthOrderQuantity(), dashboardFillRateDTO.getCurrentMonthQuantity());
        this.previousMonthFillRate = calculateFillRate(dashboardFillRateDTO.getPreviousMonthOrderQuantity(), dashboardFillRateDTO.getPreviousMonthQuantity());
    }

    public double calculateFillRate(int orderQty, int qty) {
        return (double) qty / orderQty * 100;
    }

}
