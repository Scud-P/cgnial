package com.cgnial.salesreports.domain.DTO.distributorSales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlyDistributorSalesDTO {
    private int year;
    private List<QuarterlyDistributorSalesDTO> quarterlySales;

    public YearlyDistributorSalesDTO(POSSaleDTO saleDTO) {
        this.year = saleDTO.getYear();
        this.quarterlySales = new ArrayList<>();
    }

}
