package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.DTO.*;
import com.cgnial.salesreports.repositories.PurchaseOrderProductRepository;
import com.cgnial.salesreports.util.DatesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DistributorCasesService {

    private static final Logger logger = LoggerFactory.getLogger(DistributorCasesService.class);

    @Autowired
    private PurchaseOrderProductRepository purchaseOrderProductRepository;

    @Autowired
    private DatesUtil datesUtil;

    public List<CaseOrderDTO> getAllCases() {
        return  purchaseOrderProductRepository.findAll()
                .stream()
                .map(CaseOrderDTO::new)
                .toList();
    }

    public List<CasesByYearDTO> getSalesByDistributorByYear() {
        List<CaseOrderDTO> allOrders = getAllCases();

        return allOrders.stream()
                .collect(Collectors.groupingBy(order ->
                        new AbstractMap.SimpleEntry<>(order.getYear(), order.getDistributor().toUpperCase())))
                .entrySet()
                .stream()
                .map(entry -> {
                    int year = entry.getKey().getKey();
                    String distributor = entry.getKey().getValue();
                    List<CaseOrderDTO> distributorOrders = entry.getValue();

                    CasesByYearDTO aggregatedDto = new CasesByYearDTO();
                    aggregatedDto.setYear(year);
                    aggregatedDto.setDistributor(distributor);

                    distributorOrders.forEach(order -> {
                        aggregatedDto.setOneHundredQty(aggregatedDto.getOneHundredQty() + order.getOneHundredQty());
                        aggregatedDto.setOneHundredTwoQty(aggregatedDto.getOneHundredTwoQty() + order.getOneHundredTwoQty());
                        aggregatedDto.setOneHundredThreeQty(aggregatedDto.getOneHundredThreeQty() + order.getOneHundredThreeQty());
                        aggregatedDto.setOneHundredFourQty(aggregatedDto.getOneHundredFourQty() + order.getOneHundredFourQty());
                        aggregatedDto.setOneHundredFiveQty(aggregatedDto.getOneHundredFiveQty() + order.getOneHundredFiveQty());
                        aggregatedDto.setOneHundredEightQty(aggregatedDto.getOneHundredEightQty() + order.getOneHundredEightQty());
                        aggregatedDto.setOneHundredTenQty(aggregatedDto.getOneHundredTenQty() + order.getOneHundredTenQty());
                        aggregatedDto.setOneHundredElevenQty(aggregatedDto.getOneHundredElevenQty() + order.getOneHundredElevenQty());
                        aggregatedDto.setOneHundredTwelveQty(aggregatedDto.getOneHundredTwelveQty() + order.getOneHundredTwelveQty());
                        aggregatedDto.setOneHundredThirteenQty(aggregatedDto.getOneHundredThirteenQty() + order.getOneHundredThirteenQty());
                        aggregatedDto.setOneHundredFourteenQty(aggregatedDto.getOneHundredFourteenQty() + order.getOneHundredFourteenQty());
                        aggregatedDto.setOneHundredFifteenQty(aggregatedDto.getOneHundredFifteenQty() + order.getOneHundredFifteenQty());
                        aggregatedDto.setOneHundredSeventeenQty(aggregatedDto.getOneHundredSeventeenQty() + order.getOneHundredSeventeenQty());
                        aggregatedDto.setOneHundredTwentyFiveQty(aggregatedDto.getOneHundredTwentyFiveQty() + order.getOneHundredTwentyFiveQty());
                        aggregatedDto.setOneHundredTwentySixQty(aggregatedDto.getOneHundredTwentySixQty() + order.getOneHundredTwentySixQty());
                        aggregatedDto.setOneHundredTwentySevenQty(aggregatedDto.getOneHundredTwentySevenQty() + order.getOneHundredTwentySevenQty());
                        aggregatedDto.setOneHundredTwentyEightQty(aggregatedDto.getOneHundredTwentyEightQty() + order.getOneHundredTwentyEightQty());
                        aggregatedDto.setOneHundredThirtyQty(aggregatedDto.getOneHundredThirtyQty() + order.getOneHundredThirtyQty());
                        aggregatedDto.setOneHundredThirtyOneQty(aggregatedDto.getOneHundredThirtyOneQty() + order.getOneHundredThirtyOneQty());
                        aggregatedDto.setTwoHundredQty(aggregatedDto.getTwoHundredQty() + order.getTwoHundredQty());
                        aggregatedDto.setTwoHundredTwoQty(aggregatedDto.getTwoHundredTwoQty() + order.getTwoHundredTwoQty());
                        aggregatedDto.setTwoHundredFourQty(aggregatedDto.getTwoHundredFourQty() + order.getTwoHundredFourQty());
                        aggregatedDto.setTwoHundredFiveQty(aggregatedDto.getTwoHundredFiveQty() + order.getTwoHundredFiveQty());
                        aggregatedDto.setTwoHundredTwentyFiveQty(aggregatedDto.getTwoHundredTwentyFiveQty() + order.getTwoHundredTwentyFiveQty());
                    });
                    logger.info("Aggregated DTO : {}", aggregatedDto);
                    return aggregatedDto;
                })
                .toList();
    }

    public List<CasesByDistributorByYearDTO> getCasesByDistributorByYearDTO() {
        // Step 1: Get the consolidated orders
        List<CasesByYearDTO> consolidatedOrders = getSalesByDistributorByYear();

        // Step 2: Group by distributor
        return consolidatedOrders.stream()
                .collect(Collectors.groupingBy(CasesByYearDTO::getDistributor))
                .entrySet()
                .stream()
                .map(entry -> {
                    String distributor = entry.getKey();
                    List<CasesByYearDTO> distributorCases = entry.getValue();

                    // Step 3: Map to CasesByDistributorByYearDTO
                    Map<Integer, CasesByYearDTO> casesByYearMap = distributorCases.stream()
                            .collect(Collectors.toMap(
                                    CasesByYearDTO::getYear,
                                    dto -> dto,
                                    (existing, newDto) -> {
                                        // Aggregate quantities if the year already exists
                                        existing.setOneHundredQty(existing.getOneHundredQty() + newDto.getOneHundredQty());
                                        existing.setOneHundredTwoQty(existing.getOneHundredTwoQty() + newDto.getOneHundredTwoQty());
                                        existing.setOneHundredThreeQty(existing.getOneHundredThreeQty() + newDto.getOneHundredThreeQty());
                                        existing.setOneHundredFourQty(existing.getOneHundredFourQty() + newDto.getOneHundredFourQty());
                                        existing.setOneHundredFiveQty(existing.getOneHundredFiveQty() + newDto.getOneHundredFiveQty());
                                        existing.setOneHundredEightQty(existing.getOneHundredEightQty() + newDto.getOneHundredEightQty());
                                        existing.setOneHundredTenQty(existing.getOneHundredTenQty() + newDto.getOneHundredTenQty());
                                        existing.setOneHundredElevenQty(existing.getOneHundredElevenQty() + newDto.getOneHundredElevenQty());
                                        existing.setOneHundredTwelveQty(existing.getOneHundredTwelveQty() + newDto.getOneHundredTwelveQty());
                                        existing.setOneHundredThirteenQty(existing.getOneHundredThirteenQty() + newDto.getOneHundredThirteenQty());
                                        existing.setOneHundredFourteenQty(existing.getOneHundredFourteenQty() + newDto.getOneHundredFourteenQty());
                                        existing.setOneHundredFifteenQty(existing.getOneHundredFifteenQty() + newDto.getOneHundredFifteenQty());
                                        existing.setOneHundredSeventeenQty(existing.getOneHundredSeventeenQty() + newDto.getOneHundredSeventeenQty());
                                        existing.setOneHundredTwentyFiveQty(existing.getOneHundredTwentyFiveQty() + newDto.getOneHundredTwentyFiveQty());
                                        existing.setOneHundredTwentySixQty(existing.getOneHundredTwentySixQty() + newDto.getOneHundredTwentySixQty());
                                        existing.setOneHundredTwentySevenQty(existing.getOneHundredTwentySevenQty() + newDto.getOneHundredTwentySevenQty());
                                        existing.setOneHundredTwentyEightQty(existing.getOneHundredTwentyEightQty() + newDto.getOneHundredTwentyEightQty());
                                        existing.setOneHundredThirtyQty(existing.getOneHundredThirtyQty() + newDto.getOneHundredThirtyQty());
                                        existing.setOneHundredThirtyOneQty(existing.getOneHundredThirtyOneQty() + newDto.getOneHundredThirtyOneQty());
                                        existing.setTwoHundredQty(existing.getTwoHundredQty() + newDto.getTwoHundredQty());
                                        existing.setTwoHundredTwoQty(existing.getTwoHundredTwoQty() + newDto.getTwoHundredTwoQty());
                                        existing.setTwoHundredFourQty(existing.getTwoHundredFourQty() + newDto.getTwoHundredFourQty());
                                        existing.setTwoHundredFiveQty(existing.getTwoHundredFiveQty() + newDto.getTwoHundredFiveQty());
                                        existing.setTwoHundredTwentyFiveQty(existing.getTwoHundredTwentyFiveQty() + newDto.getTwoHundredTwentyFiveQty());
                                        return existing;
                                    }
                            ));

                    // Create a list from the aggregated results
                    List<CasesByYearDTO> casesByYearList = new ArrayList<>(casesByYearMap.values());

                    // Create the final DTO for distributor
                    CasesByDistributorByYearDTO dto = new CasesByDistributorByYearDTO();
                    dto.setDistributor(distributor);
                    dto.setCasesByYear(casesByYearList);

                    logger.info("Distributor DTO : {}", dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<CasesPerDistributorDTO> getSalesByDistributorByYearAndQuarter() {

        return null;
    }

    public int determineQuarter(LocalDate poDate) {
        int month = poDate.getMonthValue();
        return datesUtil.determineQuarter(month);
    }

}
