package com.cgnial.salesreports.service.distributorSales;

import com.cgnial.salesreports.domain.DTO.distributorSales.POSSaleDTO;
import com.cgnial.salesreports.domain.DTO.distributorSales.QuarterlyDistributorSalesDTO;
import com.cgnial.salesreports.domain.DTO.distributorSales.YearlyDistributorSalesDTO;
import com.cgnial.salesreports.domain.DTO.distributorSalesByGroup.GroupPOSSaleDTO;
import com.cgnial.salesreports.domain.DTO.distributorSalesByGroup.QuarterlySalesByAccountDTO;
import com.cgnial.salesreports.domain.DTO.distributorSalesByGroup.YearlySalesByAccountDTO;
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
import java.util.stream.Collectors;

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

    // Case 0 because we initialize empty DTOs (without ItemNumber)
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

    public List<GroupPOSSaleDTO> getTenBestAccounts(String distributor) {

        List<GroupPOSSaleDTO> salesByAccount = posSalesRepository.findByDistributor(distributor).stream()
                .map(GroupPOSSaleDTO::new)
                .toList();

        Map<String, Double> totalSalesByAccount = salesByAccount.stream()
                .collect(Collectors.groupingBy(GroupPOSSaleDTO::getAccount,
                        Collectors.summingDouble(GroupPOSSaleDTO::getAmount)));

        List<GroupPOSSaleDTO> aggregatedSales = totalSalesByAccount.entrySet().stream()
                .map(entry -> new GroupPOSSaleDTO(entry.getKey(), entry.getValue(), 0, 0))
                .sorted((a, b) -> Double.compare(b.getAmount(), a.getAmount()))
                .limit(10)
                .toList();

        return aggregatedSales;
    }

    public List<YearlySalesByAccountDTO> getBestGroupSalesByYear(String distributor) {

        List<GroupPOSSaleDTO> salesByAccount = posSalesRepository.findByDistributor(distributor).stream()
                .map(GroupPOSSaleDTO::new)
                .toList();

        Map<String, Double> totalSalesByAccount = salesByAccount.stream()
                .filter(sale -> sale.getAccount() != null) // Filter out null accounts to prevent NullPointerException
                .collect(Collectors.groupingBy(
                        GroupPOSSaleDTO::getAccount,
                        Collectors.summingDouble(GroupPOSSaleDTO::getAmount)
                ));

        List<String> top10Accounts = totalSalesByAccount.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();

        List<GroupPOSSaleDTO> topSalesByAccount = salesByAccount.stream()
                .filter(sale -> top10Accounts.contains(sale.getAccount()))
                .toList();

        Map<Integer, YearlySalesByAccountDTO> salesByAccountMap = new HashMap<>();

        for(GroupPOSSaleDTO saleDTO : topSalesByAccount) {
            int year = saleDTO.getYear();
            int quarter = saleDTO.getQuarter();
            double amount = saleDTO.getAmount();
            String account = saleDTO.getAccount();

            YearlySalesByAccountDTO yearlySalesByAccount = salesByAccountMap.get(year);
            if(yearlySalesByAccount == null) {
                yearlySalesByAccount = new YearlySalesByAccountDTO(year, new ArrayList<>());
                salesByAccountMap.put(year, yearlySalesByAccount);
            }

            QuarterlySalesByAccountDTO quarterlySalesByAccountDTO = yearlySalesByAccount.getQuarterlySalesByAccount().stream()
                    .filter(q -> q.getQuarter() == quarter)
                    .findFirst()
                    .orElse(null);

            if(quarterlySalesByAccountDTO == null) {
                quarterlySalesByAccountDTO = new QuarterlySalesByAccountDTO(quarter, new ArrayList<>());
                yearlySalesByAccount.getQuarterlySalesByAccount().add(quarterlySalesByAccountDTO);
            }
            incrementQuantityForAccount(quarterlySalesByAccountDTO, account, amount);
        }
        return new ArrayList<>(salesByAccountMap.values());
    }

    private void incrementQuantityForAccount(QuarterlySalesByAccountDTO quarterlySalesByAccountDTO, String account, double amount) {
        // Ensure `account` is not null
        if (account == null) {
            return;
        }
        // Find the existing account sales for this account if it exists
        GroupPOSSaleDTO existingAccountSales = quarterlySalesByAccountDTO.getSalesByAccount().stream()
                .filter(s -> account.equals(s.getAccount())) // Add null-safe equals check
                .findFirst()
                .orElse(null);

        if (existingAccountSales != null) {
            // Update the amount for the existing account by adding the new sale amount
            existingAccountSales.setAmount(existingAccountSales.getAmount() + amount);
        } else {
            // Add a new entry if the account doesn't exist in the list
            quarterlySalesByAccountDTO.getSalesByAccount().add(new GroupPOSSaleDTO(account, amount, quarterlySalesByAccountDTO.getQuarter(), quarterlySalesByAccountDTO.getQuarter()));
        }
    }
}
