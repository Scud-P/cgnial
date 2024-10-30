package com.cgnial.salesreports.domain.DTO.cases;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CasesPerDistributorDTO {
    private String distributor;
    private List<YearlyCasesDTO> yearlyCases;
}
