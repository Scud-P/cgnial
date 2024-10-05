package com.cgnial.salesreports.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuarterlySalesDTO {
    private int quarter;
    private double sales;
}
