package com.cgnial.salesreports.service.fillRates;

import com.cgnial.salesreports.domain.DTO.fillRates.FillRateDTO;
import com.cgnial.salesreports.domain.DTO.fillRates.MonthlyFillRatesBySkuDTO;
import com.cgnial.salesreports.domain.DTO.fillRates.YearlyFillRatesBySkuDTO;
import com.cgnial.salesreports.repositories.POSSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FillRatesService {

    @Autowired
    private POSSalesRepository posSalesRepository;


    public List<YearlyFillRatesBySkuDTO> getYearlyFillRatesBySKU() {

        String distributor = "unfi";

        List<FillRateDTO> fillRates = posSalesRepository.findByDistributor(distributor)
                .stream()
                .map(FillRateDTO::new)
                .toList();

        Map<Integer, YearlyFillRatesBySkuDTO> fillRatesMap = new HashMap<>();

        for (FillRateDTO dto : fillRates) {
            int year = dto.getYear();
            int month = dto.getMonth();
            int itemNumber = dto.getItemNumber();
            double missedSale = dto.getMissedSale();

            YearlyFillRatesBySkuDTO yearlyRates = fillRatesMap.get(year);
            if (yearlyRates == null) {
                yearlyRates = new YearlyFillRatesBySkuDTO(year, new ArrayList<>());
                fillRatesMap.put(year, yearlyRates);
            }

            MonthlyFillRatesBySkuDTO monthlyFillRatesBySkuDTO = yearlyRates.getMonthlyFillRates()
                    .stream()
                    .filter(m -> m.getMonth() == month)
                    .findFirst()
                    .orElse(null);

            if (monthlyFillRatesBySkuDTO == null) {
                monthlyFillRatesBySkuDTO = new MonthlyFillRatesBySkuDTO(month, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0);
                yearlyRates.getMonthlyFillRates().add(monthlyFillRatesBySkuDTO);
            }
            incrementMissedSalesForItemNumber(monthlyFillRatesBySkuDTO, itemNumber, missedSale);
        }
        return new ArrayList<>(fillRatesMap.values());
    }

    public void incrementMissedSalesForItemNumber(MonthlyFillRatesBySkuDTO dtoToIncrement, int itemNumber, double amount) {
        switch (itemNumber) {
            case 100 -> dtoToIncrement.setOneHundredMissedSales(dtoToIncrement.getOneHundredMissedSales() + amount);
            case 102 ->
                    dtoToIncrement.setOneHundredTwoMissedSales(dtoToIncrement.getOneHundredTwoMissedSales() + amount);
            case 103 ->
                    dtoToIncrement.setOneHundredThreeMissedSales(dtoToIncrement.getOneHundredThreeMissedSales() + amount);
            case 104 ->
                    dtoToIncrement.setOneHundredFourMissedSales(dtoToIncrement.getOneHundredFourMissedSales() + amount);
            case 105 ->
                    dtoToIncrement.setOneHundredFiveMissedSales(dtoToIncrement.getOneHundredFiveMissedSales() + amount);
            case 108 ->
                    dtoToIncrement.setOneHundredEightMissedSales(dtoToIncrement.getOneHundredEightMissedSales() + amount);
            case 110 ->
                    dtoToIncrement.setOneHundredTenMissedSales(dtoToIncrement.getOneHundredTenMissedSales() + amount);
            case 111 ->
                    dtoToIncrement.setOneHundredElevenMissedSales(dtoToIncrement.getOneHundredElevenMissedSales() + amount);
            case 112 ->
                    dtoToIncrement.setOneHundredTwelveMissedSales(dtoToIncrement.getOneHundredTwelveMissedSales() + amount);
            case 113 ->
                    dtoToIncrement.setOneHundredThirteenMissedSales(dtoToIncrement.getOneHundredThirteenMissedSales() + amount);
            case 114 ->
                    dtoToIncrement.setOneHundredFourteenMissedSales(dtoToIncrement.getOneHundredFourteenMissedSales() + amount);
            case 115 ->
                    dtoToIncrement.setOneHundredFifteenMissedSales(dtoToIncrement.getOneHundredFifteenMissedSales() + amount);
            case 117 ->
                    dtoToIncrement.setOneHundredSeventeenMissedSales(dtoToIncrement.getOneHundredSeventeenMissedSales() + amount);
            case 125 ->
                    dtoToIncrement.setOneHundredTwentyFiveMissedSales(dtoToIncrement.getOneHundredTwentyFiveMissedSales() + amount);
            case 126 ->
                    dtoToIncrement.setOneHundredTwentySixMissedSales(dtoToIncrement.getOneHundredTwentySixMissedSales() + amount);
            case 127 ->
                    dtoToIncrement.setOneHundredTwentySevenMissedSales(dtoToIncrement.getOneHundredTwentySevenMissedSales() + amount);
            case 128 ->
                    dtoToIncrement.setOneHundredTwentyEightMissedSales(dtoToIncrement.getOneHundredTwentyEightMissedSales() + amount);
            case 130 ->
                    dtoToIncrement.setOneHundredThirtyMissedSales(dtoToIncrement.getOneHundredThirtyMissedSales() + amount);
            case 131 ->
                    dtoToIncrement.setOneHundredThirtyOneMissedSales(dtoToIncrement.getOneHundredThirtyOneMissedSales() + amount);
            case 200 -> dtoToIncrement.setTwoHundredMissedSales(dtoToIncrement.getTwoHundredMissedSales() + amount);
            case 202 ->
                    dtoToIncrement.setTwoHundredTwoMissedSales(dtoToIncrement.getTwoHundredTwoMissedSales() + amount);
            case 204 ->
                    dtoToIncrement.setTwoHundredFourMissedSales(dtoToIncrement.getTwoHundredFourMissedSales() + amount);
            case 205 ->
                    dtoToIncrement.setTwoHundredFiveMissedSales(dtoToIncrement.getTwoHundredFiveMissedSales() + amount);
            case 225 ->
                    dtoToIncrement.setTwoHundredTwentyFiveMissedSales(dtoToIncrement.getTwoHundredTwentyFiveMissedSales() + amount);
            case 0 -> {
            }
            default -> throw new IllegalArgumentException("Invalid item number: " + itemNumber);
        }
    }

    public List<YearlyFillRatesBySkuDTO> getFillRatesForCurrentMonthReport() {

        String distributor = "unfi";

        List<FillRateDTO> fillRates = posSalesRepository.findByDistributor(distributor)
                .stream()
                .map(FillRateDTO::new)
                .toList();

// Find the most recent year
        Optional<Integer> mostRecentYearOpt = fillRates.stream()
                .map(FillRateDTO::getYear)
                .max(Comparator.naturalOrder());

        if (mostRecentYearOpt.isPresent()) {
            int mostRecentYear = mostRecentYearOpt.get();

            // Find the most recent month for that year
            Optional<Integer> mostRecentMonthOpt = fillRates.stream()
                    .filter(dto -> dto.getYear() == mostRecentYear)
                    .map(FillRateDTO::getMonth)
                    .max(Comparator.naturalOrder());

            if (mostRecentMonthOpt.isPresent()) {
                int mostRecentMonth = mostRecentMonthOpt.get();

                // Filter fillRates to keep only the most recent year and month
                List<FillRateDTO> filteredFillRates = fillRates.stream()
                        .filter(dto -> dto.getYear() == mostRecentYear && dto.getMonth() == mostRecentMonth)
                        .toList();

                // Use the filtered list as needed
                fillRates = filteredFillRates;

                Map<Integer, YearlyFillRatesBySkuDTO> fillRatesMap = new HashMap<>();

                for (FillRateDTO dto : fillRates) {
                    int year = dto.getYear();
                    int month = dto.getMonth();
                    int itemNumber = dto.getItemNumber();
                    double missedSale = dto.getMissedSale();

                    YearlyFillRatesBySkuDTO yearlyRates = fillRatesMap.get(year);
                    if (yearlyRates == null) {
                        yearlyRates = new YearlyFillRatesBySkuDTO(year, new ArrayList<>());
                        fillRatesMap.put(year, yearlyRates);
                    }

                    MonthlyFillRatesBySkuDTO monthlyFillRatesBySkuDTO = yearlyRates.getMonthlyFillRates()
                            .stream()
                            .filter(m -> m.getMonth() == month)
                            .findFirst()
                            .orElse(null);

                    if (monthlyFillRatesBySkuDTO == null) {
                        monthlyFillRatesBySkuDTO = new MonthlyFillRatesBySkuDTO(month, 0, 0, 0,
                                0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0);
                        yearlyRates.getMonthlyFillRates().add(monthlyFillRatesBySkuDTO);
                    }
                    incrementMissedSalesForItemNumber(monthlyFillRatesBySkuDTO, itemNumber, missedSale);
                }
                return new ArrayList<>(fillRatesMap.values());
            }
        }
        return new ArrayList<>();
    }
}