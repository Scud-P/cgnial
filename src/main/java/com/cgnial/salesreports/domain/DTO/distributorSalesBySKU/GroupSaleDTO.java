package com.cgnial.salesreports.domain.DTO.distributorSalesBySKU;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupSaleDTO {
    private int year;
    private int quarter;
    private double amount;
}
