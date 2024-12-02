package com.cgnial.salesreports.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "formulas")
public class Formula {

    @Id
    private String id;
    private String name;
    private List<Ingredient> proportions;
    private Integer totalRatio;

}
