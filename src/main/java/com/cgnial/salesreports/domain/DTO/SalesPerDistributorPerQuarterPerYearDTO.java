package com.cgnial.salesreports.domain.DTO;

import lombok.Data;

@Data
public class SalesPerDistributorPerQuarterPerYearDTO {
    private String distributor;
    private String quarter;
    private int year;
    private double sales;
}
