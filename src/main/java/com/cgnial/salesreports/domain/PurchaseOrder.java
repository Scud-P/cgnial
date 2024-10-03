package com.cgnial.salesreports.domain;

import lombok.Data;

@Data
public class PurchaseOrder {

    private String poDate;
    private String distributor;
    private int amount;

}
