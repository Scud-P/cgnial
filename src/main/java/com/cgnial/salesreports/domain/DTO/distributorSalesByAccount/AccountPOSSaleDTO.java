package com.cgnial.salesreports.domain.DTO.distributorSalesByAccount;

import com.cgnial.salesreports.domain.POSSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountPOSSaleDTO {

    private String account;
    private double amount;
    private int quarter;
    private int year;

    public AccountPOSSaleDTO(POSSale posSale){
        this.account = posSale.getCustomerName();
        this.amount = posSale.getAmount();
        this.quarter = posSale.getQuarter();
        this.year = posSale.getYear();
    }

}
