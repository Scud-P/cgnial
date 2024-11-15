package com.cgnial.salesreports.domain.DTO.fillRates;

import com.cgnial.salesreports.domain.POSSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FillRateDTO {

    private int itemNumber;
    private int year;
    private int month;
    private double missedSale;

    public FillRateDTO(POSSale posSale) {
        this.itemNumber = posSale.getItemNumber();
        this.year = posSale.getYear();
        this.month = posSale.getMonth();
        this.missedSale = (posSale.getOrderAmount() - posSale.getAmount());
    }

}
