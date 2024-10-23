package com.cgnial.salesreports.domain.DTO;

import com.cgnial.salesreports.domain.Product;
import lombok.Data;

@Data
public class ProductDetailsDTO {

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

    public ProductDetailsDTO(Product product) {
        this.id = product.getId();
        this.coutuCode = product.getCoutuCode();
        this.frenchDescription = product.getFrenchDescription();
        this.englishDescription = product.getEnglishDescription();
        this.size = product.getSize();
        this.uom = product.getUom();
        this.caseSize = product.getCaseSize();
        this.caseUpc = product.getCaseUpc();
        this.unitUpc = product.getUnitUpc();
        this.unfiCode = product.getUnfiCode();
        this.satauCode = product.getSatauCode();
        this.puresourceCode = product.getPuresourceCode();
        this.unitsPerCase = product.getUnitsPerCase();
    }

}
