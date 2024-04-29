package com.cgnial.salesreports.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;
    @Column(name = "coutu_code")
    private String coutuCode;
    @Column(name = "coutu_description")
    private String coutuDescription;
    @Column(name = "coutu_format")
    private String coutuUnitFormat;
    @Column(name = "coutu_uc")
    private String unitsPerCase;
    @Column(name = "format_category")
    private String formatCategory;
    @Column(name = "product_family")
    private String productFamily;
    @Column(name = "unfi_code")
    private String unfiCode;
    @Column(name = "satau_code")
    private String satauCode;
    @Column(name = "puresource_code")
    private String puresourceCode;

}
