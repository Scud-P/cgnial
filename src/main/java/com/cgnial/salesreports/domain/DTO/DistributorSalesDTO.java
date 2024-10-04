package com.cgnial.salesreports.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DistributorSalesDTO {
    private String distributor;
    private List<SalesByYearDTO> salesByYear;
}
