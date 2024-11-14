package com.cgnial.salesreports.domain.DTO.distributorSalesByGroup;

import com.cgnial.salesreports.domain.POSSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupSaleDTO {
    private int year;
    private int quarter;
    private double amount;

    public GroupSaleDTO(POSSale posSale) {
        this.year = posSale.getYear();
        this.quarter = posSale.getQuarter();
        this.amount = posSale.getAmount();
    }
}
