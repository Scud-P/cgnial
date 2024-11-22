package com.cgnial.salesreports.domain.DTO.mcb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class McbDTO {

    double currentYearMcbAmount;
    double currentYearSalesAmount;
    double currentYearMcbPercentage;
    double previousYearMcbAmount;
    double previousYearSalesAmount;
    double previousYearMcbPercentage;

}
