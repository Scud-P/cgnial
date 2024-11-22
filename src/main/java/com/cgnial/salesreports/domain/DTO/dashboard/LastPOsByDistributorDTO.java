package com.cgnial.salesreports.domain.DTO.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastPOsByDistributorDTO {

    private String lastUnfiPo;
    private int daysSinceLastUnfiPo;
    private String lastSatauPo;
    private int daysSinceLastSatauPo;
    private String lastPuresourcePo;
    private int daysSinceLastPuresourcePo;
    private String lastAvrilPo;
    private int daysSinceLastAvrilPo;

}
