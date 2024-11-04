package com.cgnial.salesreports.domain.parameter.distributorLoading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnfiPOSParameter {

    private String distributor = "UNFI";
    private int year;
    private String month;
    private String customerName;
    private String address;
    private String city;
    private String province;
    private String zipcode;
    private String unfiItemNumber;
    private int quantity;
    private double amount;
    private int quarter;
    private String customerGroup;
    private int orderQuantity;
    private double orderAmount;
    private double mcbAmount;

}
