package com.cgnial.salesreports.domain.DTO.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardFillRateDTO {

    private int itemNumber;
    private int currentMonthQuantity = 0;
    private int currentMonthOrderQuantity = 0;
    private int previousMonthQuantity = 0;
    private int previousMonthOrderQuantity = 0;

}
