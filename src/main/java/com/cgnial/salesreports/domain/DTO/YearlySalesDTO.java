package com.cgnial.salesreports.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class YearlySalesDTO {

    private int year;
    private List<QuarterlySalesDTO> quarterlySales;

}
