package com.cgnial.salesreports.domain.DTO.distributorSalesBySKU;

import com.cgnial.salesreports.domain.POSSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SKUPOSSaleDTO {

    private int year;
    private int quarter;
    private int itemNumber;
    private double amount;

    public SKUPOSSaleDTO(POSSale posSale) {
        this.year = posSale.getYear();
        this.quarter = posSale.getQuarter();
        this.itemNumber = posSale.getItemNumber();
        this.amount = posSale.getAmount();
    }

}
