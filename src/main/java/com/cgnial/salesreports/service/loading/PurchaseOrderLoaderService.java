package com.cgnial.salesreports.service.loading;

import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.domain.PurchaseOrderProduct;
import com.cgnial.salesreports.repositories.PurchaseOrderProductRepository;
import com.cgnial.salesreports.repositories.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PurchaseOrderLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderLoaderService.class);

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderProductRepository purchaseOrderProductRepository;

    @Autowired
    private ExcelReaderService excelReaderService;

    @Autowired
    private ProductQuantityReaderService productQuantityReaderService;

    @Transactional
    public void saveAllPurchaseOrders(MultipartFile file) throws IOException {
        List<PurchaseOrder> purchaseOrders = excelReaderService.readPurchaseOrdersExcelFile(file);
        purchaseOrderRepository.saveAll(purchaseOrders);
    }

    @Transactional
    public void deleteAllPurchaseOrders() {
        purchaseOrderRepository.deleteAll();
        purchaseOrderRepository.resetAutoIncrement();
    }

    @Transactional
    public void saveAllPurchaseOrderCasesQuantity(MultipartFile file) throws IOException {
        List<PurchaseOrderProduct> purchaseOrderProducts = productQuantityReaderService.readCaseQuantities(file);
        logger.info("Found {} POs", purchaseOrderProducts.size());
        purchaseOrderProductRepository.saveAll(purchaseOrderProducts);
    }

    @Transactional
    public void deleteAllPurchaseOrderCasesQuantity() {
        purchaseOrderProductRepository.deleteAll();
        purchaseOrderProductRepository.resetAutoIncrement();
    }

    @Transactional
    public void saveInitialPos() throws IOException {
        List<PurchaseOrder> purchaseOrders = excelReaderService.readInitialPurchaseOrdersExcelFile();
        purchaseOrderRepository.saveAll(purchaseOrders);
    }

    @Transactional
    public void saveInitialPoQuantities() throws IOException {
        List<PurchaseOrderProduct> poProducts = productQuantityReaderService.readInitialCaseQuantities();
        purchaseOrderProductRepository.saveAll(poProducts);
    }
}
