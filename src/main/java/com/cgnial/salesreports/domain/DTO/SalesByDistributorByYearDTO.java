package com.cgnial.salesreports.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SalesByDistributorByYearDTO {
    String Distributor;
    List<SalesByYearDTO> salesByYear;
}
