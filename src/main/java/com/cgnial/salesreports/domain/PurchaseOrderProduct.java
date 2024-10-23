package com.cgnial.salesreports.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="purchase_orders_quantities")
public class PurchaseOrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    @Column(name="po_date")
    private String poDate;
    @Column(name="distributor")
    private String distributor;
    @Column(name="100")
    private int oneHundredQty;
    @Column(name="102")
    private int oneHundredTwoQty;
    @Column(name="103")
    private int oneHundredThreeQty;
    @Column(name="104")
    private int oneHundredFourQty;
    @Column(name="105")
    private int oneHundredFiveQty;
    @Column(name="108")
    private int oneHundredEightQty;
    @Column(name="110")
    private int oneHundredTenQty;
    @Column(name="111")
    private int oneHundredElevenQty;
    @Column(name="112")
    private int oneHundredTwelveQty;
    @Column(name="113")
    private int oneHundredThirteenQty;
    @Column(name="114")
    private int oneHundredFourteenQty;
    @Column(name="115")
    private int oneHundredFifteenQty;
    @Column(name="117")
    private int oneHundredSeventeenQty;
    @Column(name="125")
    private int oneHundredTwentyFiveQty;
    @Column(name="126")
    private int oneHundredTwentySixQty;
    @Column(name="127")
    private int oneHundredTwentySevenQty;
    @Column(name="128")
    private int oneHundredTwentyEightQty;
    @Column(name="130")
    private int oneHundredThirtyQty;
    @Column(name="131")
    private int oneHundredThirtyOneQty;
    @Column(name="200")
    private int twoHundredQty;
    @Column(name="202")
    private int twoHundredTwoQty;
    @Column(name="204")
    private int twoHundredFourQty;
    @Column(name="205")
    private int twoHundredFiveQty;
    @Column(name="225")
    private int twoHundredTwentyFiveQty;
}
