package com.cgnial.salesreports.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SalesPerDistributorDTO {
    private String distributor;
    private List<YearlySalesDTO> yearlySales;
}
