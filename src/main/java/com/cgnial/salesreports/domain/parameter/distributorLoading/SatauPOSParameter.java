package com.cgnial.salesreports.domain.parameter.distributorLoading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SatauPOSParameter {
    private String distributor = "Satau";
    private int year;
    private int month;
    private String customerName;
    private String address;
    private String city;
    private String province;
    private String zipcode;
    private String satauItemNumber;
    private int quantity;
    private double amount;
    private int quarter;
    private String customerGroup;
}
