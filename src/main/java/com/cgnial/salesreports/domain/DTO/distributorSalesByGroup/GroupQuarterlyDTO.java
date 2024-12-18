package com.cgnial.salesreports.domain.DTO.distributorSalesByGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupQuarterlyDTO {
    private int quarter;
    private double amount;
}
