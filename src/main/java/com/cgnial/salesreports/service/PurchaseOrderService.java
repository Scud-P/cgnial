package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.DTO.*;
import com.cgnial.salesreports.domain.Distributors;
import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ExcelReaderService excelReaderService;

    public void saveAllPurchaseOrders() throws IOException {
        List<PurchaseOrder> purchaseOrders = excelReaderService.readPurchaseOrdersExcelFile();
        purchaseOrderRepository.saveAll(purchaseOrders);
    }


    public void deleteAllPurchaseOrders() {
        purchaseOrderRepository.deleteAll();
    }

    public List<PurchaseOrderDTO> findAllPurchaseOrders() {
        return purchaseOrderRepository.findAll()
                .stream()
                .map(PurchaseOrderDTO::new)
                .toList();
    }

    /**
     * Retrieves a list of {@link YearlySalesByDistributorDTO} that represents the total sales by distributor for each year.
     * This method processes all purchase orders, groups them by year, and calculates the sales for each distributor (SATAU, UNFI,
     * PURESOURCE, AVRIL). The purchase orders are mapped to DTOs, which are then aggregated by year and distributor.
     *
     * @return a list of {@link YearlySalesByDistributorDTO} containing the sales data by distributor for each year.
     *
     * <p>This method performs the following steps:</p>
     * <ul>
     *     <li>Retrieves all {@link PurchaseOrder} entities from the repository.</li>
     *     <li>Iterates over each purchase order and maps it to a {@link PurchaseOrderDTO} to process the Date</li>
     *     <li>Groups the sales by year based on the purchase order date.</li>
     *     <li>Accumulates sales amounts for each distributor in the corresponding year.</li>
     *     <li>Returns a list of {@link YearlySalesByDistributorDTO} objects, one for each year.</li>
     * </ul>
     *
     * <p>The method uses a {@link HashMap} to store sales data, where the key is the year and the value is a
     * {@link YearlySalesByDistributorDTO} containing the sales data for that year.</p>
     */
    public List<YearlySalesByDistributorDTO> getYearlySalesByDistributorDTOS() {

        List<PurchaseOrder> allPos = purchaseOrderRepository.findAll();
        Map<Integer, YearlySalesByDistributorDTO> yearlySalesMap = new HashMap<>();

        for (PurchaseOrder po : allPos) {
            PurchaseOrderDTO poDTO = new PurchaseOrderDTO(po);
            int year = poDTO.getPoDate().getYear();

            YearlySalesByDistributorDTO yearlySales = yearlySalesMap.getOrDefault(year, new YearlySalesByDistributorDTO(year));
            yearlySales.addSales(poDTO.getDistributor(), poDTO.getAmount());
            yearlySalesMap.put(year, yearlySales);
        }
        return new ArrayList<>(yearlySalesMap.values());
    }


    public List<DistributorSalesDTO> getSalesData() {
        List<PurchaseOrder> allPos = purchaseOrderRepository.findAll();

        // Group by Distributor and Year, summing the amounts
        Map<String, Map<Integer, Double>> aggregatedData = allPos.stream()
                .collect(Collectors.groupingBy(
                        po -> po.getDistributor().toUpperCase(),
                        Collectors.groupingBy(
                                po -> extractYear(po.getPoDate()),
                                Collectors.summingDouble(PurchaseOrder::getAmount)
                        )
                ));

        // Convert nested maps into a list of DistributorSalesDTO
        return aggregatedData.entrySet().stream()
                .map(distributorEntry -> {
                    List<SalesByYearDTO> salesByYear = distributorEntry.getValue().entrySet().stream()
                            .map(yearEntry -> new SalesByYearDTO(yearEntry.getKey(), yearEntry.getValue()))
                            .collect(Collectors.toList());

                    return new DistributorSalesDTO(distributorEntry.getKey(), salesByYear);
                })
                .toList();
    }

//    public Map<String, Map<Integer, Double>> getSalesByDistributorByYear() {
//        List<DistributorSalesDTO> salesData = getSalesData();  // Assuming this method is already present
//
//        // Create a map where each distributor maps to a list of sales by year
//        Map<String, Map<Integer, Double>> salesMap = new LinkedHashMap<>();
//
//        for (DistributorSalesDTO dto : salesData) {
//            salesMap
//                    .computeIfAbsent(dto.getDistributor(), k -> new LinkedHashMap<>())
//                    .put(dto.getYear(), dto.getSales());
//        }
//
//        return salesMap;
//    }

//    public Map<String, Map<Integer, Double>> getSalesByQuarterByDistributorByYear() {
//        List<SalesPerDistributorPerQuarterPerYearDTO> salesData = getSalesDataByQuarter();
//
//        // Create a map where each distributor maps to a list of sales by year
//        Map<String, Map<Integer, Double>> salesMap = new LinkedHashMap<>();
//
//        for (SalesPerDistributorPerYearDTO dto : salesData) {
//            salesMap
//                    .computeIfAbsent(dto.getDistributor(), k -> new LinkedHashMap<>())
//                    .put(dto.getYear(), dto.getSales());
//        }
//
//        return salesMap;
//    }
//
//    private List<SalesPerDistributorPerQuarterPerYearDTO> getSalesDataByQuarter() {
//
//    }


    public int extractYear (String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(dateString, formatter).getYear();
    }

    private String extractQuarter(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        int quarter = (date.getMonthValue() - 1) / 3 + 1;
        return "Q"+quarter;
    }

}
