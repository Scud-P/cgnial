package com.cgnial.salesreports.domain.parameter;

import lombok.Data;

@Data
public class ProductDetailsParameter {
    private int id;
    private int coutuCode;
    private String frenchDescription;
    private String englishDescription;
    private int size;
    private String uom;
    private String caseSize;
    private int unitsPerCase;
    private String caseUpc;
    private String unitUpc;
    private String unfiCode;
    private String satauCode;
    private String oldSatauCode;
    private String puresourceCode;
    private String oldPuresourceCode;
}

