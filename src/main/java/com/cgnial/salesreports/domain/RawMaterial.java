package com.cgnial.salesreports.domain;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "raw_materials")
public class RawMaterial {

    @Id
    String id;
    String description;
    String coutuId;
    double cost;
    double yield;

}
