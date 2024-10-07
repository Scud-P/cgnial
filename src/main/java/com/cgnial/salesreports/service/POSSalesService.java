package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.domain.parameter.PuresourcePOSParameter;
import com.cgnial.salesreports.domain.parameter.SatauPOSParameter;
import com.cgnial.salesreports.repositories.POSSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class POSSalesService {

    @Autowired
    private POSSalesRepository posSalesRepository;

    @Autowired
    private ItemNumberMatchingService itemNumberMatchingService;

    @Transactional
    public void loadAllPuresourceSales(List<PuresourcePOSParameter> sales) {

        // Stream through the sales, convert each parameter to a POSSale, and inject the product code
        List<POSSale> salesToPersist = sales.stream()
                .map(parameter -> {
                    // Determine the product code based on the puresourceItemNumber
                    int productCode = itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber(parameter.getPuresourceItemNumber());
                    // Create a new POSSale object and set the product code
                    POSSale sale = new POSSale(parameter);
                    sale.setItemNumber(productCode); // Assuming POSSale has a productCode field
                    return sale;
                })
                .toList();

        // Persist all sales to the repository
        posSalesRepository.saveAll(salesToPersist);
    }

    @Transactional
    public void clearAllPuresourceSales() {
        posSalesRepository.deleteAllByDistributorIgnoreCase("Puresource");
    }

    @Transactional
    public void loadAllSatauSales(List<SatauPOSParameter> sales) {
        // Stream through the sales, convert each parameter to a POSSale, and inject the product code
        List<POSSale> salesToPersist = sales.stream()
                .map(parameter -> {
                    // Determine the product code based on the satauItemNumber
                    int productCode = itemNumberMatchingService.determineProductCodeFromSatauItemNumber(parameter.getSatauItemNumber());
                    // Create a new POSSale object and set the product code
                    POSSale sale = new POSSale(parameter);
                    sale.setItemNumber(productCode);
                    return sale;
                })
                .toList();
        // Persist all sales to the repository
        posSalesRepository.saveAll(salesToPersist);
    }

    @Transactional
    public void clearAllSatauSales() {
        posSalesRepository.deleteAllByDistributorIgnoreCase("Satau");
    }

    @Transactional
    public void resetAutoIncrement() {
        posSalesRepository.resetAutoIncrement();
    }
}
