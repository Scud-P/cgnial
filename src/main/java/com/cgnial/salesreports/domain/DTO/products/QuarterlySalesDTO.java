package com.cgnial.salesreports.domain.DTO.products;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuarterlySalesDTO {
    private int quarter;
    private double sales;
}
