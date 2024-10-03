package com.cgnial.salesreports.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    @Column(name="po_date")
    private String poDate;
    @Column(name="distributor")
    private String distributor;
    @Column(name="po_amount")
    private double amount;

}
