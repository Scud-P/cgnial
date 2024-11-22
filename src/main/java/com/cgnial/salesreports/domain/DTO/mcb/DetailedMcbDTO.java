package com.cgnial.salesreports.domain.DTO.mcb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedMcbDTO {

    private double currentYearSobeysSales;
    private double currentYearSobeysMcb;
    private double currentYearSobeysMcbPercentage;
    private double previousYearSobeysSales;
    private double previousYearSobeysMcb;
    private double previousYearSobeysMcbPercentage;
    private double currentYearMetroSales;
    private double currentYearMetroMcb;
    private double currentYearMetroMcbPercentage;
    private double previousYearMetroSales;
    private double previousYearMetroMcb;
    private double previousYearMetroMcbPercentage;
    private double currentYearWfmSales;
    private double currentYearWfmMcb;
    private double currentYearWfmMcbPercentage;
    private double previousYearWfmSales;
    private double previousYearWfmMcb;
    private double previousYearWfmMcbPercentage;
}
