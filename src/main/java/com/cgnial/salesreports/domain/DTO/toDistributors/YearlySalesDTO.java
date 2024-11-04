package com.cgnial.salesreports.domain.DTO.toDistributors;

import com.cgnial.salesreports.domain.DTO.products.QuarterlySalesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class YearlySalesDTO {

    private int year;
    private List<QuarterlySalesDTO> quarterlySales;

}
