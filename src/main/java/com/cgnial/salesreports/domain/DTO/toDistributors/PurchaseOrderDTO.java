package com.cgnial.salesreports.domain.DTO.toDistributors;

import com.cgnial.salesreports.domain.PurchaseOrder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class PurchaseOrderDTO {

    private Integer id;
    private LocalDate poDate;
    private String distributor;
    private double amount;

    public PurchaseOrderDTO(PurchaseOrder po) {
        this.id = po.getId();
        this.poDate = toLocalDate(po.getPoDate());
        this.distributor = po.getDistributor();
        this.amount = po.getAmount();
    }

    public LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(dateString, formatter);
    }

}
