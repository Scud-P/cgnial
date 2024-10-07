package com.cgnial.salesreports.domain;

import com.cgnial.salesreports.domain.parameter.PuresourcePOSParameter;
import com.cgnial.salesreports.domain.parameter.SatauPOSParameter;
import com.cgnial.salesreports.domain.parameter.UnfiPOSParameter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pos_sales")
public class POSSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="distributor")
    private String distributor;
    @Column(name="year")
    private int year;
    @Column(name="month")
    private int month;
    @Column(name="customer_name")
    private String customerName;
    @Column(name="customer_group")
    private String customerGroup;
    @Column(name="address")
    private String address;
    @Column(name="city")
    private  String city;
    @Column(name="province")
    private String province;
    @Column(name="zipcode")
    private  String zipcode;
    @Column(name="quantity")
    private int quantity;
    @Column(name="amount")
    private double amount;
    @Column(name="quarter")
    private int quarter;
    @Column(name="item_number")
    private int itemNumber;
    @Column(name="order_quantity")
    private int orderQuantity;
    @Column(name="order_amount")
    private double orderAmount;

    public POSSale(PuresourcePOSParameter parameter) {
        this.distributor = parameter.getDistributor();
        this.year = parameter.getYear();
        this.month = parameter.getMonth();
        this.customerName = parameter.getCustomerName();
        this.address = parameter.getAddress();
        this.city = parameter.getCity();
        this.province = parameter.getProvince();
        this.zipcode = parameter.getZipcode();
        this.quantity = parameter.getQuantity();
        this.amount = parameter.getAmount();
        this.quarter = parameter.getQuarter();
    }

    public POSSale(SatauPOSParameter parameter) {
        this.distributor = parameter.getDistributor();
        this.year = parameter.getYear();
        this.month = parameter.getMonth();
        this.customerName = parameter.getCustomerName();
        this.customerGroup = parameter.getCustomerGroup();
        this.address = parameter.getAddress();
        this.city = parameter.getCity();
        this.province = parameter.getProvince();
        this.zipcode = parameter.getZipcode();
        this.quantity = parameter.getQuantity();
        this.amount = parameter.getAmount();
        this.quarter = parameter.getQuarter();
    }

    public POSSale(UnfiPOSParameter parameter) {
        this.distributor = parameter.getDistributor();
        this.year = parameter.getYear();
        this.customerName = parameter.getCustomerName();
        this.customerGroup = parameter.getCustomerGroup();
        this.address = parameter.getAddress();
        this.city = parameter.getCity();
        this.province = parameter.getProvince();
        this.zipcode = parameter.getZipcode();
        this.quantity = parameter.getQuantity();
        this.amount = parameter.getAmount();
        this.quarter = parameter.getQuarter();
        this.orderQuantity = parameter.getOrderQuantity();
        this.orderAmount = parameter.getOrderAmount();
    }
}
