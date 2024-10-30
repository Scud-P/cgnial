package com.cgnial.salesreports.domain.DTO.cases;

import com.cgnial.salesreports.domain.PurchaseOrderProduct;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseOrderDTO {
    private long id;
    private int year;
    private int quarter;
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

    public CaseOrderDTO(PurchaseOrderProduct po) {
        this.id = po.getId();
        this.quarter = extractQuarter(po.getPoDate());
        this.year = extractYear(po.getPoDate());
        this.distributor = po.getDistributor();
        this.oneHundredQty = po.getOneHundredQty();
        this.oneHundredTwoQty = po.getOneHundredTwoQty();
        this.oneHundredThreeQty = po.getOneHundredThreeQty();
        this.oneHundredFourQty = po.getOneHundredFourQty();
        this.oneHundredFiveQty = po.getOneHundredFiveQty();
        this.oneHundredEightQty = po.getOneHundredEightQty();
        this.oneHundredTenQty = po.getOneHundredTenQty();
        this.oneHundredElevenQty = po.getOneHundredElevenQty();
        this.oneHundredTwelveQty = po.getOneHundredTwelveQty();
        this.oneHundredThirteenQty = po.getOneHundredThirteenQty();
        this.oneHundredFourteenQty = po.getOneHundredFourteenQty();
        this.oneHundredFifteenQty = po.getOneHundredFifteenQty();
        this.oneHundredSeventeenQty = po.getOneHundredSeventeenQty();
        this.oneHundredTwentyFiveQty = po.getOneHundredTwentyFiveQty();
        this.oneHundredTwentySixQty = po.getOneHundredTwentySixQty();
        this.oneHundredTwentySevenQty = po.getOneHundredTwentySevenQty();
        this.oneHundredTwentyEightQty = po.getOneHundredTwentyEightQty();
        this.oneHundredThirtyQty = po.getOneHundredThirtyQty();
        this.oneHundredThirtyOneQty = po.getOneHundredThirtyOneQty();
        this.twoHundredQty = po.getTwoHundredQty();
        this.twoHundredTwoQty = po.getTwoHundredTwoQty();
        this.twoHundredFourQty = po.getTwoHundredFourQty();
        this.twoHundredFiveQty = po.getTwoHundredFiveQty();
        this.twoHundredTwentyFiveQty = po.getTwoHundredTwentyFiveQty();
    }

    public int extractYear(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date.getYear();
    }

    public int extractQuarter(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        int month = date.getMonthValue();
        return (month - 1) / 3 + 1;
    }

}

