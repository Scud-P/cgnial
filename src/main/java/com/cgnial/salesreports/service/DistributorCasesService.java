package com.cgnial.salesreports.service;

import com.cgnial.salesreports.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributorCasesService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;



}
