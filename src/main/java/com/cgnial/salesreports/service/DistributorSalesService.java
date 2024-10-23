package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.DTO.*;
import com.cgnial.salesreports.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DistributorSalesService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public List<PurchaseOrderDTO> getAllSales() {

        return purchaseOrderRepository.findAll()
                .stream()
                .map(PurchaseOrderDTO::new)
                .toList();
    }

    public List<SalesByDistributorByYearDTO> getSalesByDistributorByYear() {
        List<PurchaseOrderDTO> allPos = getAllSales();

        return allPos.stream()
                // Group by distributor first
                .collect(Collectors.groupingBy(po -> po.getDistributor().toUpperCase()))
                .entrySet()
                .stream()
                .map(entry -> {
                    String distributor = entry.getKey();
                    List<PurchaseOrderDTO> distributorPos = entry.getValue();

                    // Group by year within each distributor's purchase orders
                    Map<Integer, Double> salesByYear = distributorPos.stream()
                            .collect(Collectors.groupingBy(
                                    // Extract the year from the LocalDate
                                    po -> po.getPoDate().getYear(),
                                    // Sum the sales amount for each year
                                    Collectors.summingDouble(PurchaseOrderDTO::getAmount)
                            ));

                    // Convert the map into a list of SalesByYearDTO
                    List<SalesByYearDTO> salesByYearList = salesByYear.entrySet()
                            .stream()
                            .map(yearEntry -> new SalesByYearDTO(yearEntry.getKey(), yearEntry.getValue()))
                            .toList();

                    // Create and return the SalesByDistributorByYearDTO for this distributor
                    return new SalesByDistributorByYearDTO(distributor, salesByYearList);
                })
                .collect(Collectors.toList());
    }

    public List<SalesPerDistributorDTO> getSalesByDistributorByYearAndQuarter() {
        List<PurchaseOrderDTO> allPos = getAllSales();

        return allPos.stream()
                // Group by distributor first
                .collect(Collectors.groupingBy(po -> po.getDistributor().toUpperCase()))
                .entrySet()
                .stream()
                .map(entry -> {
                    String distributor = entry.getKey();
                    List<PurchaseOrderDTO> distributorPos = entry.getValue();

                    // Group by year within each distributor's purchase orders
                    Map<Integer, List<PurchaseOrderDTO>> salesByYear = distributorPos.stream()
                            .collect(Collectors.groupingBy(
                                    po -> po.getPoDate().getYear()
                            ));

                    // For each year, group by quarter and sum the sales
                    List<YearlySalesDTO> yearlySalesDTOs = salesByYear.entrySet()
                            .stream()
                            .map(yearEntry -> {
                                int year = yearEntry.getKey();
                                List<PurchaseOrderDTO> yearPos = yearEntry.getValue();

                                // Initialize the salesByQuarter map with 0 values for all 4 quarters
                                Map<Integer, Double> salesByQuarter = new HashMap<>();
                                for (int quarter = 1; quarter <= 4; quarter++) {
                                    salesByQuarter.put(quarter, 0.0);
                                }

                                // Populate the map with actual sales data for each quarter
                                salesByQuarter.putAll(yearPos.stream()
                                        .collect(Collectors.groupingBy(
                                                po -> (po.getPoDate().getMonthValue() - 1) / 3 + 1,
                                                Collectors.summingDouble(PurchaseOrderDTO::getAmount)
                                        )));  // Override the sales for quarters that have sales

                                // Convert the sales by quarter map into a list of QuarterlySalesDTOs
                                List<QuarterlySalesDTO> quarterlySalesDTOs = salesByQuarter.entrySet()
                                        .stream()
                                        .map(quarterEntry -> new QuarterlySalesDTO(quarterEntry.getKey(), quarterEntry.getValue()))
                                        .toList();

                                // Create a YearlySalesDTO for this year
                                return new YearlySalesDTO(year, quarterlySalesDTOs);
                            })
                            .toList();

                    // Create and return the SalesPerDistributorDTO for this distributor
                    return new SalesPerDistributorDTO(distributor, yearlySalesDTOs);
                })
                .collect(Collectors.toList());
    }

}
