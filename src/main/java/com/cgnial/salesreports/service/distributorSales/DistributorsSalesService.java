package com.cgnial.salesreports.service.distributorSales;

import com.cgnial.salesreports.domain.DTO.distributorSales.POSSaleDTO;
import com.cgnial.salesreports.domain.DTO.distributorSales.QuarterlyDistributorSalesDTO;
import com.cgnial.salesreports.domain.DTO.distributorSales.YearlyDistributorSalesDTO;
import com.cgnial.salesreports.domain.DTO.distributorSalesByAccount.AccountPOSSaleDTO;
import com.cgnial.salesreports.domain.DTO.distributorSalesByGroup.*;
import com.cgnial.salesreports.domain.DTO.distributorSalesBySKU.*;
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

    public List<GroupYearlyDTO> getSalesForMassGroup(String distributor, String group) {

        List<GroupSaleDTO> metroSales = posSalesRepository.findSalesByDistributorAndGroup(distributor, group)
                .stream()
                .map(GroupSaleDTO::new)
                .toList();

        Map<Integer, GroupYearlyDTO> salesByAccountMap = new HashMap<>();

        for (GroupSaleDTO saleDTO : metroSales) {
            int year = saleDTO.getYear();
            int quarter = saleDTO.getQuarter();
            double amount = saleDTO.getAmount();

            // Create or retrieve yearly data object
            GroupYearlyDTO yearlySales = salesByAccountMap.get(year);
            if (yearlySales == null) {
                yearlySales = new GroupYearlyDTO(year, new ArrayList<>());
                salesByAccountMap.put(year, yearlySales);
            }

            GroupQuarterlyDTO quarterlySales = yearlySales.getSalesByQuarter().stream()
                    .filter(q -> q.getQuarter() == quarter)
                    .findFirst()
                    .orElse(null);

            if (quarterlySales == null) {
                quarterlySales = new GroupQuarterlyDTO(quarter, 0.0);
                yearlySales.getSalesByQuarter().add(quarterlySales);
            } else {
                quarterlySales.setAmount(quarterlySales.getAmount() + amount);
            }
        }
        // Step 8: Return the final list, after applying the exclusions
        return new ArrayList<>(salesByAccountMap.values());
    }


    public List<YearlySalesByAccountDTO> getBestGroupSalesByYear(String distributor) {

        // Step 1: Fetch sales data from repository and map to DTOs
        List<GroupPOSSaleDTO> salesByAccount = posSalesRepository.findByDistributor(distributor).stream()
                .map(GroupPOSSaleDTO::new)
                .toList();

        // Step 2: List of accounts to exclude if distributor is UNFI
        List<String> excludedUNFIAccounts = distributor.equalsIgnoreCase("unfi") ? excludedUnfiAccounts() : List.of();
        List<String> excludedSatauGroups = distributor.equalsIgnoreCase("satau") ? satauExcludedGroups() : List.of();


        // Step 3: Filter out the excluded accounts early in the stream pipeline
        salesByAccount = salesByAccount.stream()
                .filter(sale -> sale.getAccount() != null) // Filter out null accounts
                .filter(sale -> !excludedUNFIAccounts.contains(sale.getAccount()))
                .filter(sale -> !excludedSatauGroups.contains(sale.getAccount()))// Exclude unwanted accounts
                .toList();

        // Step 4: Calculate total sales by account (only considering non-excluded accounts)
        Map<String, Double> totalSalesByAccount = salesByAccount.stream()
                .collect(Collectors.groupingBy(
                        GroupPOSSaleDTO::getAccount,
                        Collectors.summingDouble(GroupPOSSaleDTO::getAmount)
                ));

        // Step 5: Get top 10 accounts by total sales (already excluding unwanted accounts)
        List<String> top10Accounts = totalSalesByAccount.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Sort by amount
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();

        // Step 6: Filter sales data to include only the top 10 accounts
        List<GroupPOSSaleDTO> topSalesByAccount = salesByAccount.stream()
                .filter(sale -> top10Accounts.contains(sale.getAccount())) // Only top 10 accounts
                .toList();

        // Step 7: Group sales data by year and quarter
        Map<Integer, YearlySalesByAccountDTO> salesByAccountMap = new HashMap<>();

        for (GroupPOSSaleDTO saleDTO : topSalesByAccount) {
            int year = saleDTO.getYear();
            int quarter = saleDTO.getQuarter();
            double amount = saleDTO.getAmount();
            String account = saleDTO.getAccount();

            // Create or retrieve yearly data object
            YearlySalesByAccountDTO yearlySalesByAccount = salesByAccountMap.get(year);
            if (yearlySalesByAccount == null) {
                yearlySalesByAccount = new YearlySalesByAccountDTO(year, new ArrayList<>());
                salesByAccountMap.put(year, yearlySalesByAccount);
            }

            // Create or retrieve quarterly data object
            QuarterlySalesByAccountDTO quarterlySalesByAccountDTO = yearlySalesByAccount.getQuarterlySalesByAccount().stream()
                    .filter(q -> q.getQuarter() == quarter)
                    .findFirst()
                    .orElse(null);

            if (quarterlySalesByAccountDTO == null) {
                quarterlySalesByAccountDTO = new QuarterlySalesByAccountDTO(quarter, new ArrayList<>());
                yearlySalesByAccount.getQuarterlySalesByAccount().add(quarterlySalesByAccountDTO);
            }

            // Increment quantity for account in the quarterly sales data
            incrementQuantityForAccount(quarterlySalesByAccountDTO, account, amount);
        }

        // Step 8: Return the final list, after applying the exclusions
        return new ArrayList<>(salesByAccountMap.values());
    }

    public List<YearlySalesByAccountDTO> getBestAccountSalesByYear(String distributor) {

        // Step 1: Fetch sales data from repository and map to DTOs
        List<AccountPOSSaleDTO> salesByAccount = posSalesRepository.findByDistributor(distributor).stream()
                .map(AccountPOSSaleDTO::new)
                .toList();

        // Step 2: List of accounts to exclude if distributor is UNFI
        List<String> excludedUNFIAccounts = distributor.equalsIgnoreCase("unfi") ? excludedUnfiAccounts() : List.of();
        List<String> excludedSatauAccounts = distributor.equalsIgnoreCase("satau") ? satauExcludedAccounts() : List.of();


        // Step 3: Filter out the excluded accounts early in the stream pipeline
        salesByAccount = salesByAccount.stream()
                .filter(sale -> sale.getAccount() != null) // Filter out null accounts
                .filter(sale -> !excludedUNFIAccounts.contains(sale.getAccount()))
                .filter(sale -> !excludedSatauAccounts.contains(sale.getAccount()))// Exclude unwanted accounts
                .toList();

        // Step 4: Calculate total sales by account (only considering non-excluded accounts)
        Map<String, Double> totalSalesByAccount = salesByAccount.stream()
                .collect(Collectors.groupingBy(
                        AccountPOSSaleDTO::getAccount,
                        Collectors.summingDouble(AccountPOSSaleDTO::getAmount)
                ));

        // Step 5: Get top 10 accounts by total sales (already excluding unwanted accounts)
        List<String> top10Accounts = totalSalesByAccount.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Sort by amount
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();

        // Step 6: Filter sales data to include only the top 10 accounts
        List<AccountPOSSaleDTO> topSalesByAccount = salesByAccount.stream()
                .filter(sale -> top10Accounts.contains(sale.getAccount())) // Only top 10 accounts
                .toList();

        // Step 7: Group sales data by year and quarter
        Map<Integer, YearlySalesByAccountDTO> salesByAccountMap = new HashMap<>();

        for (AccountPOSSaleDTO saleDTO : topSalesByAccount) {
            int year = saleDTO.getYear();
            int quarter = saleDTO.getQuarter();
            double amount = saleDTO.getAmount();
            String account = saleDTO.getAccount();

            // Create or retrieve yearly data object
            YearlySalesByAccountDTO yearlySalesByAccount = salesByAccountMap.get(year);
            if (yearlySalesByAccount == null) {
                yearlySalesByAccount = new YearlySalesByAccountDTO(year, new ArrayList<>());
                salesByAccountMap.put(year, yearlySalesByAccount);
            }

            // Create or retrieve quarterly data object
            QuarterlySalesByAccountDTO quarterlySalesByAccountDTO = yearlySalesByAccount.getQuarterlySalesByAccount().stream()
                    .filter(q -> q.getQuarter() == quarter)
                    .findFirst()
                    .orElse(null);

            if (quarterlySalesByAccountDTO == null) {
                quarterlySalesByAccountDTO = new QuarterlySalesByAccountDTO(quarter, new ArrayList<>());
                yearlySalesByAccount.getQuarterlySalesByAccount().add(quarterlySalesByAccountDTO);
            }

            // Increment quantity for account in the quarterly sales data
            incrementQuantityForAccount(quarterlySalesByAccountDTO, account, amount);
        }

        // Step 8: Return the final list, after applying the exclusions
        return new ArrayList<>(salesByAccountMap.values());
    }


    // Method that returns the list of accounts to exclude when distributor is "UNFI"
    public List<String> excludedUnfiAccounts() {
        return List.of(
                "METRO QUEBEC", "SOBEYS QUEBEC", "FIELD SALES INDIE", "CUSTOMER EXP INDIE"
        );
    }

    // Method that returns the list of accounts to exclude when distributor is "UNFI"
    public List<String> satauExcludedGroups() {
        return List.of(
                "MOIS004 Client",
                "SUPE012 Client"
        );
    }

    public List<String> satauExcludedAccounts() {
        return List.of(
                "LA BOITE A GRAINS (GATINEAU)",
                "LA BOITE A GRAINS (PLATEAU)",
                "S. BOURASSA(MT-TREMBL.LTEE",
                "TAU ALIMENTS NATURELS (Laval)",
                "LA BOITE A GRAINS (HULL)",
                "TAU ALIM.NAT(POINTE-CLAIRE)",
                "9228-6632 QUEBEC INC. (POINTE-CLAIRE)",
                "9152-6491 QUEBEC INC. (LANGELIER)",
                "S.BOURASSA ( St-Janvier) Ltee",
                "TAU ALIMENTS NATURELS LANGE",
                "S.BOURASSA(STE-AGATHE)LTEE",
                "Tau Alim. Naturels (Brossard)",
                "TAU BLAINVILLE / 9365-0075 QUEBEC INC.",
                "EPICERIES P.A. NATURE INC.",
                "Groupe ADONIS Inc(marche Adonis-dix 30)",
                "Les Marches d'Aliments Naturels TAU INC.(BROSSARD)",
                "S.E.C.PASQUIER",
                "9228-6632 QUEBEC INC.",
                "GROUPE ADONIS (division Gatineau)",
                "ADONIS OTTAWA",
                "9365-0075 QUEBEC INC. (BLAINVILLE)",
                "BIO SATTVA",
                "9228-6632 QUEBEC INC.",
                "LA BOITE A GRAINS (AYLMER)"
        );
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
