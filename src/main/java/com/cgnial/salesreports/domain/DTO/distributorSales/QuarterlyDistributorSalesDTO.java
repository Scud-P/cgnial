package com.cgnial.salesreports.domain.DTO.distributorSales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuarterlyDistributorSalesDTO {
    private int quarter;
    private double amount;
}
