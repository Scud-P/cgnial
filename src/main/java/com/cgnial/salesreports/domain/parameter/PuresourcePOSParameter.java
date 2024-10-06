package com.cgnial.salesreports.domain.parameter;

import com.cgnial.salesreports.domain.POSSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuresourcePOSParameter {

    private String distributor = "Puresource";
    private int year;
    private int month;
    private String customerName;
    private String address;
    private String city;
    private String province;
    private String zipcode;
    private String puresourceItemNumber;
    private int quantity;
    private double amount;
    private int quarter;

}
