package com.cgnial.salesreports.domain.DTO.distributorSales;

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
    private int year;

    public AccountPOSSaleDTO(POSSale posSale) {
    }
}
