package com.cgnial.salesreports.domain.DTO.cases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuarterlySatauSalesDTO {
    private int quarter;
    private double amount;
}
