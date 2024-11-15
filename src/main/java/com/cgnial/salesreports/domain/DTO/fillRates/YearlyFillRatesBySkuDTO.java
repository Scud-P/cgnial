package com.cgnial.salesreports.domain.DTO.fillRates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlyFillRatesBySkuDTO {
    private int year;
    private List<MonthlyFillRatesBySkuDTO> monthlyFillRates;
}
