package com.cgnial.salesreports.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String productId;
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
    private String puresourceCode;
}
