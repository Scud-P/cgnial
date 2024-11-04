package com.cgnial.salesreports.domain.DTO.toDistributors;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SalesByYearDTO {
    private int year;
    private double sales;
}


