package com.cgnial.salesreports.domain.DTO;

import lombok.Data;

@Data
public class CasesPerPODTO {
    private int coutuCode;
    private int quarter;
    private int year;
    private int quantity;
}
