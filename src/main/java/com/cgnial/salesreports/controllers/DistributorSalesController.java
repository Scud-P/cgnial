package com.cgnial.salesreports.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/distributorSales")
public class DistributorSalesController {

    @GetMapping("/list")
    public String getDistributorSalesList() {
        return "po/list";
    }

}
