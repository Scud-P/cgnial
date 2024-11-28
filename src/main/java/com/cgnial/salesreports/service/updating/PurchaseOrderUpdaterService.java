package com.cgnial.salesreports.service.updating;

import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.domain.PurchaseOrderProduct;
import com.cgnial.salesreports.repositories.PurchaseOrderProductRepository;
import com.cgnial.salesreports.repositories.PurchaseOrderRepository;
import com.cgnial.salesreports.service.loading.ExcelReaderService;
import com.cgnial.salesreports.service.loading.ProductQuantityReaderService;
import com.cgnial.salesreports.service.loading.PurchaseOrderLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PurchaseOrderUpdaterService {

    @Autowired
    private PurchaseOrderRepository poRepository;

    @Autowired
    private PurchaseOrderProductRepository poProductRepository;

    @Autowired
    private PurchaseOrderLoaderService purchaseOrderLoaderService;

    @Autowired
    private ProductQuantityReaderService productQuantityReaderService;

    @Autowired
    private ExcelReaderService excelReaderService;

    public PurchaseOrder findLastSavedPO() {
        return poRepository.findLastPurchaseorder();
    }

    // TODO PO AMOUNT MUST BE DOUBLE IN DB

    @Transactional
    public List<PurchaseOrder> updatePurchaseOrders(MultipartFile file) throws IOException {
        poRepository.deleteAll();
        poRepository.resetAutoIncrement();
        List<PurchaseOrder> updatedPurchaseOrders = excelReaderService.readPurchaseOrdersExcelFile(file);
        poRepository.saveAll(updatedPurchaseOrders);
        return updatedPurchaseOrders;
    }

    @Transactional
    public List<PurchaseOrderProduct> updatePurchaseOrderProducts(MultipartFile file) throws IOException {
        poProductRepository.deleteAll();
        poProductRepository.resetAutoIncrement();
        List<PurchaseOrderProduct> updatedPoProducts = productQuantityReaderService.readCaseQuantities(file);
        poProductRepository.saveAll(updatedPoProducts);
        return updatedPoProducts;
    }
}

