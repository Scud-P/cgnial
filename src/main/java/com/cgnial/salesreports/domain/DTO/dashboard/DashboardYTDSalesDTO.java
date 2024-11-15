package com.cgnial.salesreports.domain.DTO.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardYTDSalesDTO {

    private String distributor;
    private double totalAmount;

}
