package com.cgnial.salesreports.domain.DTO.toDistributors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SalesByDistributorByYearDTO {
    private String Distributor;
    private List<SalesByYearDTO> salesByYear;
}
