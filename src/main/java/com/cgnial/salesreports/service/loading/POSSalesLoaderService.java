package com.cgnial.salesreports.service.loading;

import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.domain.parameter.distributorLoading.PuresourcePOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.SatauPOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.UnfiPOSParameter;
import com.cgnial.salesreports.repositories.POSSalesRepository;
import com.cgnial.salesreports.util.DatesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class POSSalesLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(POSSalesLoaderService.class);


    @Autowired
    private POSSalesRepository posSalesRepository;

    @Autowired
    private ItemNumberMatchingService itemNumberMatchingService;

    @Autowired
    private DatesUtil datesUtil;

    @Transactional
    public void loadAllPuresourceSales(List<PuresourcePOSParameter> sales) {

        List<String> discontinuedProducts = getDiscontinuedPuresourceCodes();
        Set<String> missingCodes = new HashSet<>();

        List<POSSale> salesToPersist = sales.stream()
                .filter(parameter -> !discontinuedProducts.contains(parameter.getPuresourceItemNumber()))
                .map(parameter -> {
                    int productCode = itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber(parameter.getPuresourceItemNumber());

                    if (productCode == 0) {
                        productCode = itemNumberMatchingService.determineProductCodeFromOldPuresourceItemNumber(parameter.getPuresourceItemNumber());
                        logger.info("Found old product code {}, assigning it to Coutu Code {}", parameter.getPuresourceItemNumber(), productCode);
                    }

                    if (productCode != 0) {
                        POSSale sale = new POSSale(parameter);
                        sale.setItemNumber(productCode);
                        return sale;
                    } else {
                        missingCodes.add(parameter.getPuresourceItemNumber());
                        return null; // Return null to filter out later
                    }
                })
                .filter(Objects::nonNull) // Filter out nulls (i.e., sales with missing codes)
                .toList();

        logger.info("Missing Codes: {} ", missingCodes);
        posSalesRepository.saveAll(salesToPersist);
    }

    @Transactional
    public void clearAllPuresourceSales() {
        posSalesRepository.deleteAllByDistributorIgnoreCase("Puresource");
    }

    @Transactional
    public void loadAllSatauSales(List<SatauPOSParameter> sales) {

        List<String> excludedCodes = getSatauExcludedCodes();
        Set<String> missingCodes = new HashSet<>();

        // Stream through the sales, convert each parameter to a POSSale, and inject the product code
        List<POSSale> salesToPersist = sales.stream()
                .filter(parameter -> !excludedCodes.contains(parameter.getSatauItemNumber()))
                .map(parameter -> {
                    // Determine the product code based on the satauItemNumber
                    int productCode = itemNumberMatchingService.determineProductCodeFromSatauItemNumber(parameter.getSatauItemNumber());

                    if (productCode == 0) {
                        productCode = itemNumberMatchingService.determineProductCodeFromOldSatauItemNumber(parameter.getSatauItemNumber());
                        logger.info("Found old Satau product code {}, assigning it to Coutu Code {}", parameter.getSatauItemNumber(), productCode);
                    }

                    // Create a new POSSale object and set the product code
                    POSSale sale = new POSSale(parameter);
                    sale.setItemNumber(productCode);
                    if(sale.getItemNumber() == 0) {
                        missingCodes.add(parameter.getSatauItemNumber());
                    }
                    return sale;
                })
                .toList();
        logger.info("Missing Satau Codes: {} ", missingCodes);
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

    @Transactional
    public void loadAllUnfiSales(List<UnfiPOSParameter> sales) {
        List<POSSale> salesToPersist = sales.stream()
                .map(parameter -> {
                    // Determine the product code based on the unfiItemNumber
                    int productCode = itemNumberMatchingService.determineProductCodeFromUnfiItemNumber(parameter.getUnfiItemNumber());
                    int month = datesUtil.convertMonthToIntValue(parameter.getMonth());
                    int quarter = datesUtil.determineQuarter(month);
                    POSSale sale = new POSSale(parameter);
                    sale.setItemNumber(productCode);
                    sale.setMonth(month);
                    sale.setQuarter(quarter);
                    return sale;
                })
                .toList();
        // Persist all sales to the repository
        posSalesRepository.saveAll(salesToPersist);
    }

    @Transactional
    public void clearAllUnfiSales() {
        posSalesRepository.deleteAllByDistributorIgnoreCase("UNFI");
    }

    public List<String> getDiscontinuedPuresourceCodes() {
        return List.of(
                "SOL00083", "SOL00085", "SOL00084", "SOL00086", "SOL00066", "SOL00014", "SOL00069"
        );
    }


    public List<String> getSatauExcludedCodes() {
        return List.of(
                "CUIFN021", "CUIMC011", "CUIFR023", "CUILG001", "CUILG003", "CUILG005", "CUILG007",
                "CUILG101", "CUILG103", "CUILG105", "CUILG107", "CUIFT021", "CUIFP021", "CUIFP013", "CUIFM021",
                "CUIMT023", "CUIMT021", "CUIMC001", "CUIFA021", "CUIFQ021", "CUIFS021");
    }

    @Transactional
    public void clearAllSales() {
        posSalesRepository.deleteAll();
    }
}
