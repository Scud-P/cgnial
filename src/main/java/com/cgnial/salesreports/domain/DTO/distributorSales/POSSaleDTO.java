package com.cgnial.salesreports.domain.DTO.distributorSales;

import com.cgnial.salesreports.domain.POSSale;
import lombok.Data;

@Data
public class POSSaleDTO {

    private String distributor;
    private int year;
    private int quarter;
    private double amount;

    public POSSaleDTO(POSSale sale) {
        this.distributor = sale.getDistributor();
        this.year = sale.getYear();
        this.quarter = sale.getQuarter();
        this.amount = sale.getAmount();
    }

}
