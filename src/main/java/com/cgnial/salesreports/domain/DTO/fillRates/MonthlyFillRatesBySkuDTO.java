package com.cgnial.salesreports.domain.DTO.fillRates;

import com.cgnial.salesreports.domain.POSSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyFillRatesBySkuDTO {

    private int month;
    private double oneHundredMissedSales;
    private double oneHundredTwoMissedSales;
    private double oneHundredThreeMissedSales;
    private double oneHundredFourMissedSales;
    private double oneHundredFiveMissedSales;
    private double oneHundredEightMissedSales;
    private double oneHundredTenMissedSales;
    private double oneHundredElevenMissedSales;
    private double oneHundredTwelveMissedSales;
    private double oneHundredThirteenMissedSales;
    private double oneHundredFourteenMissedSales;
    private double oneHundredFifteenMissedSales;
    private double oneHundredSeventeenMissedSales;
    private double oneHundredTwentyFiveMissedSales;
    private double oneHundredTwentySixMissedSales;
    private double oneHundredTwentySevenMissedSales;
    private double oneHundredTwentyEightMissedSales;
    private double oneHundredThirtyMissedSales;
    private double oneHundredThirtyOneMissedSales;
    private double twoHundredMissedSales;
    private double twoHundredTwoMissedSales;
    private double twoHundredFourMissedSales;
    private double twoHundredFiveMissedSales;
    private double twoHundredTwentyFiveMissedSales;

    public MonthlyFillRatesBySkuDTO(POSSale posSale) {
        this.month = posSale.getMonth();

        this.oneHundredMissedSales = calculateMissedSales(posSale, 100);
        this.oneHundredTwoMissedSales = calculateMissedSales(posSale, 102);
        this.oneHundredThreeMissedSales = calculateMissedSales(posSale, 103);
        this.oneHundredFourMissedSales = calculateMissedSales(posSale, 104);
        this.oneHundredFiveMissedSales = calculateMissedSales(posSale, 105);
        this.oneHundredEightMissedSales = calculateMissedSales(posSale, 108);
        this.oneHundredTenMissedSales = calculateMissedSales(posSale, 110);
        this.oneHundredElevenMissedSales = calculateMissedSales(posSale, 111);
        this.oneHundredTwelveMissedSales = calculateMissedSales(posSale, 112);
        this.oneHundredThirteenMissedSales = calculateMissedSales(posSale, 113);
        this.oneHundredFourteenMissedSales = calculateMissedSales(posSale, 114);
        this.oneHundredFifteenMissedSales = calculateMissedSales(posSale, 115);
        this.oneHundredSeventeenMissedSales = calculateMissedSales(posSale, 117);
        this.oneHundredTwentyFiveMissedSales = calculateMissedSales(posSale, 125);
        this.oneHundredTwentySixMissedSales = calculateMissedSales(posSale, 126);
        this.oneHundredTwentySevenMissedSales = calculateMissedSales(posSale, 127);
        this.oneHundredTwentyEightMissedSales = calculateMissedSales(posSale, 128);
        this.oneHundredThirtyMissedSales = calculateMissedSales(posSale, 130);
        this.oneHundredThirtyOneMissedSales = calculateMissedSales(posSale, 131);
        this.twoHundredMissedSales = calculateMissedSales(posSale, 200);
        this.twoHundredTwoMissedSales = calculateMissedSales(posSale, 202);
        this.twoHundredFourMissedSales = calculateMissedSales(posSale, 204);
        this.twoHundredFiveMissedSales = calculateMissedSales(posSale, 205);
        this.twoHundredTwentyFiveMissedSales = calculateMissedSales(posSale, 225);
    }

    private double calculateMissedSales(POSSale posSale, int sku) {
        if (posSale.getItemNumber() == sku) {
            return posSale.getOrderQuantity() - posSale.getQuantity();
        }
        return 0.0;
    }
}
