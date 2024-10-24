package com.cgnial.salesreports.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CasesByDistributorByYearDTO {
    private String distributor;
    private List<CasesByYearDTO> casesByYear;
}
