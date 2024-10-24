package com.cgnial.salesreports.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CasesByYearDTO {
    private int year;
    private String distributor;
    private int oneHundredQty;
    private int oneHundredTwoQty;
    private int oneHundredThreeQty;
    private int oneHundredFourQty;
    private int oneHundredFiveQty;
    private int oneHundredEightQty;
    private int oneHundredTenQty;
    private int oneHundredElevenQty;
    private int oneHundredTwelveQty;
    private int oneHundredThirteenQty;
    private int oneHundredFourteenQty;
    private int oneHundredFifteenQty;
    private int oneHundredSeventeenQty;
    private int oneHundredTwentyFiveQty;
    private int oneHundredTwentySixQty;
    private int oneHundredTwentySevenQty;
    private int oneHundredTwentyEightQty;
    private int oneHundredThirtyQty;
    private int oneHundredThirtyOneQty;
    private int twoHundredQty;
    private int twoHundredTwoQty;
    private int twoHundredFourQty;
    private int twoHundredFiveQty;
    private int twoHundredTwentyFiveQty;

    public CasesByYearDTO(int year, int oneHundredQty, int oneHundredTwoQty, int oneHundredThreeQty, int oneHundredFourQty, int oneHundredFiveQty, int oneHundredEightQty, int oneHundredTenQty, int oneHundredElevenQty, int oneHundredTwelveQty, int oneHundredThirteenQty, int oneHundredFourteenQty, int oneHundredFifteenQty, int oneHundredSeventeenQty, int oneHundredTwentyFiveQty, int oneHundredTwentySixQty, int oneHundredTwentySevenQty, int oneHundredTwentyEightQty, int oneHundredThirtyQty, int oneHundredThirtyOneQty, int twoHundredQty, int twoHundredTwoQty, int twoHundredFourQty, int twoHundredFiveQty, int twoHundredTwentyFiveQty) {
    }
}
