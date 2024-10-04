package com.cgnial.salesreports.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SalesPerDistributorPerYearDTO {
    private String distributor;
    private int year;
    private double sales;
}
