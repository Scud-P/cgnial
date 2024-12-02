package com.cgnial.salesreports.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ingredients")
public class Ingredient {

    @Id
    private String id;
    private String name;
    private String rawMaterialCode;
    private double cost;

}
