package com.cgnial.salesreports.domain.DTO.cases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlySatauSalesDTO {
    private int year;
    private List<QuarterlySatauSalesDTO> quarterlySales;

    public YearlySatauSalesDTO(POSSaleDTO saleDTO) {
        this.year = saleDTO.getYear();
        this.quarterlySales = new ArrayList<>();
    }

}
