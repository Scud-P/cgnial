package com.cgnial.salesreports.service.updating;

import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.repositories.POSSalesRepository;
import com.cgnial.salesreports.service.loading.PuresourceUpdateReaderService;
import com.cgnial.salesreports.service.loading.SatauUpdateReaderService;
import com.cgnial.salesreports.service.loading.ItemNumberMatchingService;
import com.cgnial.salesreports.service.loading.UnfiUpdateReaderService;
import com.cgnial.salesreports.util.DatesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class DistributorSalesUpdaterService {

    @Autowired
    private POSSalesRepository salesRepository;

    @Autowired
    private SatauUpdateReaderService satauUpdateReaderService;

    @Autowired
    private UnfiUpdateReaderService unfiUpdateReaderService;

    @Autowired
    private PuresourceUpdateReaderService puresourceUpdateReaderService;

    @Autowired
    private ItemNumberMatchingService itemNumberMatchingService;

    @Autowired
    private DatesUtil datesUtil;

    private static final Logger logger = LoggerFactory.getLogger(DistributorSalesUpdaterService.class);

    public List<String> getSatauExcludedCodes() {
        return List.of(
                "CUIFN021", "CUIMC011", "CUIFR023", "CUILG001", "CUILG003", "CUILG005", "CUILG007",
                "CUILG101", "CUILG103", "CUILG105", "CUILG107", "CUIFT021", "CUIFP021", "CUIFP013", "CUIFM021",
                "CUIMT023", "CUIMT021", "CUIMC001", "CUIFA021", "CUIFQ021", "CUIFS021");
    }

    public POSSale findLastSavedToDbSaleForDistributor(String distributor) {
        return salesRepository.findLastSaleForDistributor(distributor);
    }

    @Transactional
    public List<POSSale> loadNewUnfiSales(MultipartFile file) throws IOException {

        int year = findLastSavedToDbSaleForDistributor("unfi").getYear();
        int week = findLastSavedToDbSaleForDistributor("unfi").getWeek();

        logger.info("Last sale saved to DB Year{}-Week{}", year, week);

        int missingSales = 0;

        List<POSSale> newUnfiSales = unfiUpdateReaderService.readUNFIPOSParameters(file).stream()
                .filter(sale ->
                        sale.getYear() > year || (sale.getYear() == year && sale.getWeek() > week)
                )
                .map(parameter -> {
                    // Determine the product code based on the unfiItemNumber
                    int productCode = itemNumberMatchingService.determineProductCodeFromUnfiItemNumber(parameter.getUnfiItemNumber());
                    if (productCode == 0) {
                        productCode = itemNumberMatchingService.determineProductCodeFromOldUnfiItemNumber(parameter.getUnfiItemNumber());
                    }
                    int month = datesUtil.convertMonthToIntValue(parameter.getMonth());
                    int quarter = datesUtil.determineQuarter(month);
                    POSSale sale = new POSSale(parameter);
                    sale.setItemNumber(productCode);
                    sale.setMonth(month);
                    sale.setQuarter(quarter);
                    logger.info("Sale found: {} ", sale);
                    return sale;
                })
                .toList();

        for (POSSale sale : newUnfiSales) {
            if (sale.getItemNumber() == 0) {
                missingSales++;
                logger.info("{}", sale);
            }
        }
        logger.info("Missing sales : {}", missingSales);
        // Persist all sales to the repository
        salesRepository.saveAll(newUnfiSales);
        return newUnfiSales;
    }

    @Transactional
    public List<POSSale> loadNewSatauSales(MultipartFile file) throws IOException {

        Set<String> missingCodes = new HashSet<>();

        int month = findLastSavedToDbSaleForDistributor("satau").getMonth();
        int year = findLastSavedToDbSaleForDistributor("satau").getYear();

        logger.info("Last found month in DB : {}", month);

        List<String> excludedCodes = getSatauExcludedCodes();

        List<POSSale> newSatauSales = satauUpdateReaderService.readSatauPOSParameters(file).stream()
                .filter(sale ->
                        sale.getYear() > year || (sale.getYear() == year && sale.getMonth() > month)
                )
                .filter(parameter -> parameter.getSatauItemNumber() != null && !excludedCodes.contains(parameter.getSatauItemNumber().trim()))
                .map(parameter -> {
                    int productCode = itemNumberMatchingService.determineProductCodeFromSatauItemNumber(parameter.getSatauItemNumber().trim());
                    logger.info("Found coutu code {} for Satau item number {}", productCode, parameter.getSatauItemNumber());
                    if (productCode == 0) {
                        productCode = itemNumberMatchingService.determineProductCodeFromOldSatauItemNumber(parameter.getSatauItemNumber().trim());
                        logger.info("Found coutu code {} for OLD Satau item number {}", productCode, parameter.getSatauItemNumber());
                    }

                    POSSale sale = new POSSale(parameter);
                    sale.setItemNumber(productCode);

                    if (sale.getItemNumber() == 0) {
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

    @Transactional
    public List<POSSale> loadNewPuresourceSales(MultipartFile file) throws Exception {

        List<String> discontinuedProducts = getDiscontinuedPuresourceCodes();
        Set<String> missingCodes = new HashSet<>();

        int month = findLastSavedToDbSaleForDistributor("puresource").getMonth();
        int year = findLastSavedToDbSaleForDistributor("puresource").getYear();

        List<POSSale> salesToPersist = puresourceUpdateReaderService.readPuresourcePOSParameters(file, month, year).stream()
                .filter(sale ->
                        sale.getYear() > year || (sale.getYear() == year && sale.getMonth() > month)
                ).filter(parameter -> !discontinuedProducts.contains(parameter.getPuresourceItemNumber()))
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

        for (POSSale sale : salesToPersist) {
            if (sale.getItemNumber() == 0) {
                logger.info("{}", sale);
            }
        }
        salesRepository.saveAll(salesToPersist);
        return salesToPersist;
    }

    public List<String> getDiscontinuedPuresourceCodes() {
        return List.of(
                "SOL00083", "SOL00085", "SOL00084", "SOL00086", "SOL00066", "SOL00014", "SOL00069"
        );
    }
}
