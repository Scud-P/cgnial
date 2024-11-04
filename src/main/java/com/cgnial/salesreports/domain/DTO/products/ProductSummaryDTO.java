package com.cgnial.salesreports.domain.DTO.products;

import com.cgnial.salesreports.domain.Product;
import lombok.Data;

@Data
public class ProductSummaryDTO {

    private int id;
    private int coutuCode;
    private String coutuDescription;
    private String size;
    private int unitsPerCase;

    public ProductSummaryDTO(Product product) {
        this.id = product.getId();
        this.coutuCode = product.getCoutuCode();
        this.coutuDescription = product.getFrenchDescription();
        this.size = product.getSize() + " " + product.getUom();
        this.unitsPerCase = product.getUnitsPerCase();
    }
}
