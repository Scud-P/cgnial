package com.cgnial.salesreports.domain.DTO.distributorSalesBySKU;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupYearlyDTO {

    private int year;
    private List<GroupQuarterlyDTO> salesByQuarter;

}
