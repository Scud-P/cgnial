package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.DTO.PurchaseOrderDTO;
import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ExcelReaderService excelReaderService;

    public void saveAllPurchaseOrders() throws IOException {
        List<PurchaseOrder> purchaseOrders = excelReaderService.readPurchaseOrdersExcelFile();
        purchaseOrderRepository.saveAll(purchaseOrders);
    }


    public void deleteAllPurchaseOrders() {
        purchaseOrderRepository.deleteAll();
    }

    public List<PurchaseOrderDTO> findAllPurchaseOrders() {
        return purchaseOrderRepository.findAll()
                .stream()
                .map(PurchaseOrderDTO::new)
                .toList();
    }
}
