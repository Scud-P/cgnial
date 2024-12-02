package com.cgnial.salesreports.service.updating;

import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.domain.PurchaseOrderProduct;
import com.cgnial.salesreports.repositories.PurchaseOrderProductRepository;
import com.cgnial.salesreports.repositories.PurchaseOrderRepository;
import com.cgnial.salesreports.service.loading.*;
import com.cgnial.salesreports.util.DatesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private PurchaseOrderReaderService poReaderService;

    @Autowired
    private DatesUtil datesUtil;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderUpdaterService.class);


    // TODO PO AMOUNT MUST BE DOUBLE IN DB

    @Transactional
    public List<PurchaseOrder> uploadPurchaseOrders(MultipartFile file) throws IOException {
        poRepository.deleteAll();
        poRepository.resetAutoIncrement();
        List<PurchaseOrder> updatedPurchaseOrders = excelReaderService.readPurchaseOrdersExcelFile(file);
        poRepository.saveAll(updatedPurchaseOrders);
        return updatedPurchaseOrders;
    }

    @Transactional
    public List<PurchaseOrderProduct> uploadPurchaseOrderProducts(MultipartFile file) throws IOException {
        poProductRepository.deleteAll();
        poProductRepository.resetAutoIncrement();
        List<PurchaseOrderProduct> updatedPoProducts = productQuantityReaderService.readCaseQuantities(file);
        poProductRepository.saveAll(updatedPoProducts);
        return updatedPoProducts;
    }

    @Transactional
    public List<PurchaseOrder> updatePurchaseOrders(MultipartFile file) throws IOException {

        List<String> retailDistributors = getRetailDistributors();

        PurchaseOrder lastPurchaseorder = poRepository.findTopByOrderByIdDesc();

        logger.info("Last PO found: {}", lastPurchaseorder);

        if(lastPurchaseorder == null) {
            return List.of();
        }

        String lastDateString = lastPurchaseorder.getPoDate();
        logger.info("Last DateString found found: {}", lastDateString);

        String lastDistributor = lastPurchaseorder.getDistributor();

        @SuppressWarnings("unchecked")
        List<PurchaseOrder> readPurchaseOrders = (List<PurchaseOrder>) poReaderService.readPurchaseOrdersUpdateExcelFile(file).get(1);

        logger.info("Read list of purchaseOrders: {}", readPurchaseOrders);

        List<PurchaseOrder> posToSave = new ArrayList<>();
        boolean shouldAdd = false;

        for (PurchaseOrder purchaseOrder : readPurchaseOrders) {
            if (shouldAdd) {
                if (retailDistributors.stream()
                        .anyMatch(distributor -> distributor.equalsIgnoreCase(purchaseOrder.getDistributor()))) {
                    posToSave.add(purchaseOrder);
                }
            } else if (purchaseOrder.getPoDate().equals(lastDateString) &&
                    purchaseOrder.getDistributor().equalsIgnoreCase(lastDistributor)) {
                shouldAdd = true;
            }
        }
        poRepository.saveAll(posToSave);
        logger.info("filtered list: {}", posToSave);
        logger.info("Number of POs added: {}", posToSave.size());
        return posToSave;
    }

    @Transactional
    public List<PurchaseOrderProduct> updatePurchaseOrderProducts(MultipartFile file) throws IOException {

        List<String> retailDistributors = getRetailDistributors();

        PurchaseOrderProduct lastPurchaseOrderproduct = poProductRepository.findTopByOrderByIdDesc();
        if (lastPurchaseOrderproduct == null) {
            return List.of();
        }

        String lastDateString = lastPurchaseOrderproduct.getPoDate();
        String lastDistributor = lastPurchaseOrderproduct.getDistributor();

        @SuppressWarnings("unchecked")
        List<PurchaseOrderProduct> readProducts = (List<PurchaseOrderProduct>)
                poReaderService.readPurchaseOrdersUpdateExcelFile(file).get(0);

        logger.info("Read list of purchaseOrderProducts: {}", readProducts);


        List<PurchaseOrderProduct> productsToSave = new ArrayList<>();
        boolean shouldAdd = false;

        for (PurchaseOrderProduct product : readProducts) {
            if (shouldAdd) {
                if (retailDistributors.stream()
                        .anyMatch(distributor -> distributor.equalsIgnoreCase(product.getDistributor()))) {
                    productsToSave.add(product);
                }
            } else if (product.getPoDate().equals(lastDateString) &&
                    product.getDistributor().equalsIgnoreCase(lastDistributor)) {
                shouldAdd = true;
            }
        }
        poProductRepository.saveAll(productsToSave);
        logger.info("Number of case POs added: {}", productsToSave.size());
        return productsToSave;
    }

    public List<String> getRetailDistributors() {
        return List.of("Satau", "Unfi", "Avril", "Puresource");
    }

}

