package com.cgnial.salesreports.domain.DTO.toDistributors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SalesPerDistributorDTO {
    private String distributor;
    private List<YearlySalesDTO> yearlySales;
}
