package com.cgnial.salesreports.domain.parameter.distributorLoading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SatauUpdateParameter {

    private String distributor = "Satau";
    private int year;
    private String yearMonth;
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
