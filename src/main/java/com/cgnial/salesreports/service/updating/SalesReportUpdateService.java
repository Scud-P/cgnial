package com.cgnial.salesreports.service.updating;

import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.repositories.POSSalesRepository;
import com.cgnial.salesreports.service.loading.ExcelUpdaterService;
import com.cgnial.salesreports.service.loading.ItemNumberMatchingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SalesReportUpdateService {

    @Autowired
    private POSSalesRepository salesRepository;

    @Autowired
    private ExcelUpdaterService updaterService;

    @Autowired
    private ItemNumberMatchingService itemNumberMatchingService;

    private static final Logger logger = LoggerFactory.getLogger(SalesReportUpdateService.class);

    public List<String> getSatauExcludedCodes() {
        return List.of(
                "CUIFN021", "CUIMC011", "CUIFR023", "CUILG001", "CUILG003", "CUILG005", "CUILG007",
                "CUILG101", "CUILG103", "CUILG105", "CUILG107", "CUIFT021", "CUIFP021", "CUIFP013", "CUIFM021",
                "CUIMT023", "CUIMT021", "CUIMC001", "CUIFA021", "CUIFQ021", "CUIFS021");
    }

    public POSSale findLastSavedToDbSaleForDistributor(String distributor) {
        return salesRepository.findLastSaleForDistributor(distributor);
    }

    public List<POSSale> loadNewSatauSales() throws IOException {

        Set<String> missingCodes = new HashSet<>();

        int month = findLastSavedToDbSaleForDistributor("satau").getMonth();

        logger.info("Last found month in DB : {}", month);

        List<String> excludedCodes = getSatauExcludedCodes();

        List<POSSale> newSatauSales = updaterService.readSatauPOSParameters().stream()
                .filter(parameter -> parameter.getMonth() > month)
                .filter(parameter -> parameter.getSatauItemNumber() != null && !excludedCodes.contains(parameter.getSatauItemNumber().trim()))
                .map(parameter -> {
                    int productCode = itemNumberMatchingService.determineProductCodeFromSatauItemNumber(parameter.getSatauItemNumber().trim());
                    logger.info("Found coutu code {} for Satau item number {}", productCode, parameter.getSatauItemNumber());
                    if(productCode == 0) {
                        productCode = itemNumberMatchingService.determineProductCodeFromOldSatauItemNumber(parameter.getSatauItemNumber().trim());
                        logger.info("Found coutu code {} for OLD Satau item number {}", productCode, parameter.getSatauItemNumber());
                    }

                    POSSale sale = new POSSale(parameter);
                    sale.setItemNumber(productCode);

                    if(sale.getItemNumber() == 0) {
                        missingCodes.add(parameter.getSatauItemNumber().trim());
                    }
                    return sale;
                })
                .toList();

        salesRepository.saveAll(newSatauSales);

        logger.info("Saving {} new Satau Sales to DB", newSatauSales.size());
        logger.info("Missing Satau codes: {}", missingCodes);
        logger.info("Number of missing codes : {}", missingCodes.size());

        return newSatauSales;
    }

}
