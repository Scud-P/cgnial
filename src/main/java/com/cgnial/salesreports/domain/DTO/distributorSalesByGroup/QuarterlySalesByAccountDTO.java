package com.cgnial.salesreports.domain.DTO.distributorSalesByGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuarterlySalesByAccountDTO {

    private int quarter;
    private List<GroupPOSSaleDTO> salesByAccount;

}
