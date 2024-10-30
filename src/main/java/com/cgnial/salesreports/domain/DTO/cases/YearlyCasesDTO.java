package com.cgnial.salesreports.domain.DTO.cases;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class YearlyCasesDTO {
    private int year;
    private List<QuarterlyCasesDTO> quarterlyCases;
}
