package com.cgnial.salesreports.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;

@Data
@Entity
@Table(name = "retail_sales")
public class RetailSale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sale_id")
    private int saleId;
    @Column(name = "coutu_code")
    private String coutuCode;
    @Column(name = "sale_year")
    private Year saleYear;
    @Column(name = "sale_month")
    private Month saleMonth;
    @Column(name = "sale_month_day")
    private MonthDay saleMonthDay;
    @Column(name = "sale_quarter")
    private String saleQuarter;
    @Column(name = "customer_number")
    private String customerNumber;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_address")
    private String customerAddress;
    @Column(name = "customer_city")
    private String customerCity;
    @Column(name = "customer_zip")
    private String customerZip;
    @Column(name = "customer_province")
    private String customerProvince;
    @Column(name = "distributor_product_code")
    private String distributorProductCode;
    @Column(name = "distributor_product_quantity")
    private String distributorProductDescription;
    @Column(name = "distributor_product_description")
    private double saleQuantity;
    @Column(name = "sale_quantity")
    private double saleAmount;
    @Column(name = "distributor")
    private String distributor;

}
