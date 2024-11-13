package com.cgnial.salesreports.domain.DTO.distributorSalesByGroup;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlySalesByAccountDTO {

    private int year;
    private List<QuarterlySalesByAccountDTO> quarterlySalesByAccount;

}
