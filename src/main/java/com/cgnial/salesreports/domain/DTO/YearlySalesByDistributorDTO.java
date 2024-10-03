package com.cgnial.salesreports.domain.DTO;

import lombok.Data;

@Data
public class YearlySalesByDistributorDTO {
    private int year;
    private double satauSales;
    private double unfiSales;
    private double puresourceSales;
    private double avrilSales;

    public YearlySalesByDistributorDTO(int year) {
        this.year = year;
    }

    public void addSales(String distributor, double amount) {
        switch (distributor.toUpperCase()) {
            case "SATAU":
                this.satauSales += amount;
                break;
            case "UNFI":
                this.unfiSales += amount;
                break;
            case "PURESOURCE":
                this.puresourceSales += amount;
                break;
            case "AVRIL":
                this.avrilSales += amount;
                break;
        }
    }
}
