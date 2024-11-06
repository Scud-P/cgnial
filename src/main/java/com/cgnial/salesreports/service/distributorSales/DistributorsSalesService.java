package com.cgnial.salesreports.service.distributorSales;

import com.cgnial.salesreports.domain.DTO.distributorSales.AccountPOSSaleDTO;
import com.cgnial.salesreports.domain.DTO.distributorSales.POSSaleDTO;
import com.cgnial.salesreports.domain.DTO.distributorSales.QuarterlyDistributorSalesDTO;
import com.cgnial.salesreports.domain.DTO.distributorSales.YearlyDistributorSalesDTO;
import com.cgnial.salesreports.domain.DTO.distributorSalesBySKU.QuarterlySalesBySKUDTO;
import com.cgnial.salesreports.domain.DTO.distributorSalesBySKU.SKUPOSSaleDTO;
import com.cgnial.salesreports.domain.DTO.distributorSalesBySKU.YearlySalesBySKUDTO;
import com.cgnial.salesreports.repositories.POSSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DistributorsSalesService {

    @Autowired
    private POSSalesRepository posSalesRepository;

    public List<POSSaleDTO> getAllSalesForDistributor(String distributor) {
        return posSalesRepository.findByDistributor(distributor)
                .stream()
                .map(POSSaleDTO::new)
                .toList();
    }

    public List<AccountPOSSaleDTO> getAllSatauSalesWithAccount(int quarter, String distributor) {
        return posSalesRepository.findByDistributorInferiorOrEqualToQuarter(distributor, quarter)
                .stream()
                .map(AccountPOSSaleDTO::new)
                .toList();
    }

    public List<YearlyDistributorSalesDTO> getDistributorQuarterlySales(String distributor) {

        List<POSSaleDTO> salesDTOs = getAllSalesForDistributor(distributor);

        Map<Integer, YearlyDistributorSalesDTO> yearlySalesMap = new HashMap<>();

        for (POSSaleDTO saleDTO : salesDTOs) {
            int year = saleDTO.getYear();
            int quarter = saleDTO.getQuarter();
            double amount = saleDTO.getAmount();

            // Check if YearlySatauSalesDTO exists for this year
            YearlyDistributorSalesDTO yearlySalesDTO = yearlySalesMap.get(year);
            if (yearlySalesDTO == null) {
                yearlySalesDTO = new YearlyDistributorSalesDTO(year, new ArrayList<>());
                yearlySalesMap.put(year, yearlySalesDTO);
            }

            // Check if QuarterlySatauSalesDTO exists for this quarter
            QuarterlyDistributorSalesDTO quarterlySalesDTO = yearlySalesDTO.getQuarterlySales().stream()
                    .filter(q -> q.getQuarter() == quarter)
                    .findFirst()
                    .orElse(null);

            // If the quarterly sales DTO does not exist, create it
            if (quarterlySalesDTO == null) {
                quarterlySalesDTO = new QuarterlyDistributorSalesDTO(quarter, 0);
                yearlySalesDTO.getQuarterlySales().add(quarterlySalesDTO);
            }

            // Update the amount for the quarter
            quarterlySalesDTO.setAmount(quarterlySalesDTO.getAmount() + amount);
        }

        // Return the list of YearlySatauSalesDTOs
        return new ArrayList<>(yearlySalesMap.values());
    }

    public List<YearlySalesBySKUDTO> getDistributorQuarterlySalesBySKU(String distributor) {

        List<SKUPOSSaleDTO> posSalesBySku = getAllSKUSalesForDistributor(distributor);

        Map<Integer, YearlySalesBySKUDTO> pOSSalesBySKUMap = new HashMap<>();

        for(SKUPOSSaleDTO posSaleBySku : posSalesBySku) {
            int year = posSaleBySku.getYear();
            int quarter = posSaleBySku.getQuarter();
            int itemNumber = posSaleBySku.getItemNumber();
            double amount = posSaleBySku.getAmount();

            YearlySalesBySKUDTO yearlySalesBySKU = pOSSalesBySKUMap.get(year);
            if(yearlySalesBySKU == null) {
                yearlySalesBySKU = new YearlySalesBySKUDTO(year, new ArrayList<>());
                pOSSalesBySKUMap.put(year, yearlySalesBySKU);
            }

            QuarterlySalesBySKUDTO quarterlySalesBySKUDTO = yearlySalesBySKU.getQuarterlySalesBySKu().stream()
                    .filter(q -> q.getQuarter() == quarter)
                    .findFirst()
                    .orElse(null);

            if(quarterlySalesBySKUDTO == null) {
                quarterlySalesBySKUDTO = new QuarterlySalesBySKUDTO(quarter, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0);
                yearlySalesBySKU.getQuarterlySalesBySKu().add(quarterlySalesBySKUDTO);
            }
            if(itemNumber == 0) {
                System.out.println(posSaleBySku);
            }
            incrementQuantityForItemNumber(quarterlySalesBySKUDTO, itemNumber, amount);
        }
        return new ArrayList<>(pOSSalesBySKUMap.values());
    }

    private List<SKUPOSSaleDTO> getAllSKUSalesForDistributor(String distributor) {
        return posSalesRepository.findByDistributor(distributor)
                .stream()
                .map(SKUPOSSaleDTO::new)
                .toList();
    }

    public void incrementQuantityForItemNumber(QuarterlySalesBySKUDTO dtoToIncrement, int itemNumber, double amount) {
        switch (itemNumber) {
            case 100 -> dtoToIncrement.setOneHundredSales(dtoToIncrement.getOneHundredSales() + amount);
            case 102 -> dtoToIncrement.setOneHundredTwoSales(dtoToIncrement.getOneHundredTwoSales() + amount);
            case 103 -> dtoToIncrement.setOneHundredThreeSales(dtoToIncrement.getOneHundredThreeSales() + amount);
            case 104 -> dtoToIncrement.setOneHundredFourSales(dtoToIncrement.getOneHundredFourSales() + amount);
            case 105 -> dtoToIncrement.setOneHundredFiveSales(dtoToIncrement.getOneHundredFiveSales() + amount);
            case 108 -> dtoToIncrement.setOneHundredEightSales(dtoToIncrement.getOneHundredEightSales() + amount);
            case 110 -> dtoToIncrement.setOneHundredTenSales(dtoToIncrement.getOneHundredTenSales() + amount);
            case 111 -> dtoToIncrement.setOneHundredElevenSales(dtoToIncrement.getOneHundredElevenSales() + amount);
            case 112 -> dtoToIncrement.setOneHundredTwelveSales(dtoToIncrement.getOneHundredTwelveSales() + amount);
            case 113 -> dtoToIncrement.setOneHundredThirteenSales(dtoToIncrement.getOneHundredThirteenSales() + amount);
            case 114 -> dtoToIncrement.setOneHundredFourteenSales(dtoToIncrement.getOneHundredFourteenSales() + amount);
            case 115 -> dtoToIncrement.setOneHundredFifteenSales(dtoToIncrement.getOneHundredFifteenSales() + amount);
            case 117 -> dtoToIncrement.setOneHundredSeventeenSales(dtoToIncrement.getOneHundredSeventeenSales() + amount);
            case 125 -> dtoToIncrement.setOneHundredTwentyFiveSales(dtoToIncrement.getOneHundredTwentyFiveSales() + amount);
            case 126 -> dtoToIncrement.setOneHundredTwentySixSales(dtoToIncrement.getOneHundredTwentySixSales() + amount);
            case 127 -> dtoToIncrement.setOneHundredTwentySevenSales(dtoToIncrement.getOneHundredTwentySevenSales() + amount);
            case 128 -> dtoToIncrement.setOneHundredTwentyEightSales(dtoToIncrement.getOneHundredTwentyEightSales() + amount);
            case 130 -> dtoToIncrement.setOneHundredThirtySales(dtoToIncrement.getOneHundredThirtySales() + amount);
            case 131 -> dtoToIncrement.setOneHundredThirtyOneSales(dtoToIncrement.getOneHundredThirtyOneSales() + amount);
            case 200 -> dtoToIncrement.setTwoHundredSales(dtoToIncrement.getTwoHundredSales() + amount);
            case 202 -> dtoToIncrement.setTwoHundredTwoSales(dtoToIncrement.getTwoHundredTwoSales() + amount);
            case 204 -> dtoToIncrement.setTwoHundredFourSales(dtoToIncrement.getTwoHundredFourSales() + amount);
            case 205 -> dtoToIncrement.setTwoHundredFiveSales(dtoToIncrement.getTwoHundredFiveSales() + amount);
            case 225 -> dtoToIncrement.setTwoHundredTwentyFiveSales(dtoToIncrement.getTwoHundredTwentyFiveSales() + amount);
            case 0 -> {}
            default -> throw new IllegalArgumentException("Invalid item number: " + itemNumber);
        }
    }



}
