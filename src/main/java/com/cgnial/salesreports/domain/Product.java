package com.cgnial.salesreports.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;

    @Column(name="coutu_code")
    private int coutuCode;
    @Column(name="french_description")
    private String frenchDescription;
    @Column(name="english_description")
    private String englishDescription;
    @Column(name="size")
    private int size;
    @Column(name="uom")
    private String uom;
    @Column(name="case_size")
    private String caseSize;
    @Column(name="units_per_case")
    private int unitsPerCase;
    @Column(name="case_upc")
    private String caseUpc;
    @Column(name="unit_upc")
    private String unitUpc;
    @Column(name="unfi_code")
    private String unfiCode;
    @Column(name="satau_code")
    private String satauCode;
    @Column(name="puresource_code")
    private String puresourceCode;
}
