package com.cgnial.salesreports.domain.DTO.distributorSalesByGroup;


import com.cgnial.salesreports.domain.POSSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupPOSSaleDTO {

    private String account;
    private double amount;
    private int quarter;
    private int year;

    public GroupPOSSaleDTO(POSSale posSale){
        this.account = posSale.getCustomerGroup();
        this.amount = posSale.getAmount();
        this.quarter = posSale.getQuarter();
        this.year = posSale.getYear();
    }

}
