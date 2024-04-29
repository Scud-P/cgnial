package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.RetailSale;
import com.cgnial.salesreports.repositories.RetailSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailSaleService {

    @Autowired
    private RetailSalesRepository retailSalesRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public RetailSale setCoutuCode(RetailSale retailSale) {

        RetailSale saleToProcess = retailSalesRepository.findById(retailSale.getSaleId())
                .orElseThrow(() -> new IllegalArgumentException("No sale found for ID" + retailSale.getSaleId()));
        String coutuCode = productService.getCoutuCode(saleToProcess.getDistributor(), saleToProcess.getDistributorProductCode());
        saleToProcess.setCoutuCode(coutuCode);
        return saleToProcess;
    }

    @Transactional
    public RetailSale addRetailSale(RetailSale retailSale) {
        setCoutuCode(retailSale);
        retailSalesRepository.save(retailSale);
        return retailSale;
    }
}
