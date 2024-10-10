package com.cgnial.salesreports.domain;

import com.cgnial.salesreports.domain.parameter.ProductDetailsParameter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table (name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
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
    @Column(name="old_satau_code")
    private String oldSatauCode;
    @Column(name="puresource_code")
    private String puresourceCode;
    @Column(name="old_puresource_code")
    private String oldPuresourceCode;

    public Product(ProductDetailsParameter productDetailsParameter) {
        this.id = productDetailsParameter.getId();
        this.coutuCode = productDetailsParameter.getCoutuCode();
        this.frenchDescription = productDetailsParameter.getFrenchDescription();
        this.englishDescription = productDetailsParameter.getEnglishDescription();
        this.size = productDetailsParameter.getSize();
        this.uom = productDetailsParameter.getUom();
        this.caseSize = productDetailsParameter.getCaseSize();
        this.unitsPerCase = productDetailsParameter.getUnitsPerCase();
        this.caseUpc = productDetailsParameter.getCaseUpc();
        this.unitUpc = productDetailsParameter.getUnitUpc();
        this.unfiCode = productDetailsParameter.getUnfiCode();
        this.satauCode = productDetailsParameter.getSatauCode();
        this.puresourceCode = productDetailsParameter.getPuresourceCode();
    }

}
