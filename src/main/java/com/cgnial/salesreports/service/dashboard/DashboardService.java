package com.cgnial.salesreports.service.dashboard;

import com.cgnial.salesreports.domain.DTO.dashboard.*;
import com.cgnial.salesreports.domain.DTO.mcb.DetailedMcbDTO;
import com.cgnial.salesreports.domain.DTO.mcb.McbDTO;
import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.repositories.POSSalesRepository;
import com.cgnial.salesreports.repositories.PurchaseOrderRepository;
import com.cgnial.salesreports.util.DatesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

    @Autowired
    private PurchaseOrderRepository poRepository;

    @Autowired
    private POSSalesRepository posSalesRepository;

    @Autowired
    private DatesUtil datesUtil;

    public LocalDate determineLastSaleDateForDistributor(String distributor) {
        String lastSaleDateString = poRepository.findLastOrderDateForDistributor(distributor);
        return datesUtil.toLocalDate(lastSaleDateString);
    }

    private int determineLastSaleIdForDistributor(String distributor) {
        return poRepository.findLastSaleIdForDistributor(distributor);
    }

    public DashboardYTDSalesDTO getYTDSalesToDistributor(String distributor) {

        List<PurchaseOrder> allPos = poRepository.findAll();
        logger.info("Found Pos for distributor{}", allPos);

        List<PurchaseOrder> debugList = poRepository.findByDistributor(distributor);
        logger.info("Found Pos {}", debugList);

        List<PurchaseOrder> allPosForDistributor = poRepository.findByDistributor(distributor)
                .stream()
                .filter(po -> po.getDistributor().equalsIgnoreCase(distributor))
                .toList();

        logger.info("Ytd sales for distributor {} : {}", distributor, allPosForDistributor);

        List<PurchaseOrderDashboardDTO> dtos = allPosForDistributor.stream()
                .map(po -> new PurchaseOrderDashboardDTO(
                        datesUtil.toLocalDate(po.getPoDate()),
                        po.getDistributor(),
                        po.getAmount()
                ))
                .toList();

        LocalDate now = LocalDate.now();

        DashboardYTDSalesDTO purchaseOrderSummaryDTO = new DashboardYTDSalesDTO();
        purchaseOrderSummaryDTO.setDistributor(distributor);
        purchaseOrderSummaryDTO.setTotalAmount(0.0);

        double totalAmountForCurrentYear = dtos.stream()
                .filter(sale -> sale.getPoDate().getYear() == now.getYear())
                .mapToDouble(PurchaseOrderDashboardDTO::getAmount)
                .sum();

        purchaseOrderSummaryDTO.setTotalAmount(totalAmountForCurrentYear);

        return purchaseOrderSummaryDTO;
    }

    public DashboardYTDSalesDTO getLYSalesToDistributor(String distributor) {

        List<PurchaseOrder> allPosForDistributor = poRepository.findByDistributor(distributor)
                .stream()
                .filter(po -> po.getDistributor().equalsIgnoreCase(distributor))
                .toList();

        List<PurchaseOrderDashboardDTO> dtos = allPosForDistributor.stream()
                .map(po -> new PurchaseOrderDashboardDTO(
                        datesUtil.toLocalDate(po.getPoDate()),
                        po.getDistributor(),
                        po.getAmount()
                ))
                .toList();

        LocalDate now = LocalDate.now();

        DashboardYTDSalesDTO purchaseOrderSummaryDTO = new DashboardYTDSalesDTO();
        purchaseOrderSummaryDTO.setDistributor(distributor);
        purchaseOrderSummaryDTO.setTotalAmount(0.0);

        LocalDate oneYearAgo = LocalDate.of(now.getYear() - 1, now.getMonthValue(), now.getDayOfMonth());

        //TODO ATTENTION IL FAUT REFACTO POUR LE 29 FÉVRIER DES ANNÉES BISSEXTILES

        double totalAmountForLastYear = dtos.stream()
                .filter(sale -> sale.getPoDate().isBefore(oneYearAgo))
                .filter(sale -> sale.getPoDate().getYear() == oneYearAgo.getYear())
                .mapToDouble(PurchaseOrderDashboardDTO::getAmount)
                .sum();

        purchaseOrderSummaryDTO.setTotalAmount(totalAmountForLastYear);
        logger.info("Purchase order summary for {} : {}", distributor, purchaseOrderSummaryDTO);

        return purchaseOrderSummaryDTO;
    }

    public DashboardToDistributorSalesSummaryDTO getTodaySummary() {

        DashboardToDistributorSalesSummaryDTO summary = new DashboardToDistributorSalesSummaryDTO();

        DashboardYTDSalesDTO unfiLy = getLYSalesToDistributor("unfi");
        DashboardYTDSalesDTO unfiYTD = getYTDSalesToDistributor("unfi");
        DashboardYTDSalesDTO satauLy = getLYSalesToDistributor("satau");
        DashboardYTDSalesDTO satauYTD = getYTDSalesToDistributor("satau");
        DashboardYTDSalesDTO puresourceLy = getLYSalesToDistributor("puresource");
        DashboardYTDSalesDTO puresourceYTD = getYTDSalesToDistributor("puresource");
        DashboardYTDSalesDTO avrilLy = getLYSalesToDistributor("avril");
        DashboardYTDSalesDTO avrilYTD = getYTDSalesToDistributor("avril");

        summary.setUnfiYTDSales(unfiYTD.getTotalAmount());
        summary.setUnfiLYSales(unfiLy.getTotalAmount());
        summary.setSatauYTDSales(satauYTD.getTotalAmount());
        summary.setSatauLYSales(satauLy.getTotalAmount());
        summary.setPuresourceYTDSales(puresourceYTD.getTotalAmount());
        summary.setPuresourceLYSales(puresourceLy.getTotalAmount());
        summary.setAvrilLYSales(avrilLy.getTotalAmount());
        summary.setAvrilYTDSales(avrilYTD.getTotalAmount());
        summary.setFromDate(LocalDate.of(LocalDate.now().getYear(), 1, 1));
        summary.setToDate(LocalDate.now());

        return summary;
    }

    public DashboardDistributorSalesSummaryDTO getDistributorSummary(String distributor) {

        List<POSSale> satauSales = posSalesRepository.findByDistributor(distributor);

        List<POSSaleDashboardDTO> satauSalesDTO = satauSales.stream()
                .map(sale -> new POSSaleDashboardDTO(
                        sale.getYear(),
                        sale.getMonth(),
                        sale.getDistributor(),
                        sale.getAmount()
                ))
                .toList();

        LocalDate now = LocalDate.now();
        int thisYear = now.getYear();
        int lastYear = thisYear - 1;
        int thisMonth = now.getMonthValue();

        List<POSSaleDashboardDTO> thisYearYTDSales = satauSalesDTO.stream()
                .filter(sale -> sale.getYear() == thisYear)
                .filter(sale -> sale.getMonth() <= thisMonth)
                .toList();

        List<POSSaleDashboardDTO> lastYearYTDSales = satauSalesDTO.stream()
                .filter(sale -> sale.getYear() == lastYear)
                .filter(sale -> sale.getMonth() <= thisMonth)
                .toList();

        double thisYearSales = thisYearYTDSales.stream().mapToDouble(POSSaleDashboardDTO::getAmount).sum();
        double lastYearSales = lastYearYTDSales.stream().mapToDouble(POSSaleDashboardDTO::getAmount).sum();

        OptionalInt lastRecordedMonthOptional = thisYearYTDSales.stream()
                .mapToInt(POSSaleDashboardDTO::getMonth)
                .max();

        int lastRecordedMonth = lastRecordedMonthOptional.orElse(-1);  // Returns -1 if no data found

        String lastUploadedSale = lastRecordedMonth + "/" + thisYear;

        return new DashboardDistributorSalesSummaryDTO(lastYearSales, thisYearSales, lastUploadedSale);
    }

    public LastPOsByDistributorDTO getLastPODates() {

        String lastUnfiPoDate = poRepository.findLastOrderDateForDistributor("unfi");
        String lastSatauPoDate = poRepository.findLastOrderDateForDistributor("satau");
        String lastPuresourcePoDate = poRepository.findLastOrderDateForDistributor("puresource");
        String lastAvrilPoDate = poRepository.findLastOrderDateForDistributor("avril");

        LocalDate unfi = datesUtil.toLocalDate(lastUnfiPoDate);
        LocalDate satau = datesUtil.toLocalDate(lastSatauPoDate);
        LocalDate puresource = datesUtil.toLocalDate(lastPuresourcePoDate);
        LocalDate avril = datesUtil.toLocalDate(lastAvrilPoDate);

        LocalDate now = LocalDate.now();

        int daysSinceLastUnfiPo = (int) ChronoUnit.DAYS.between(unfi, now);
        int daysSinceLastSatauPo = (int) ChronoUnit.DAYS.between(satau, now);
        int daysSinceLastPuresourcePo = (int) ChronoUnit.DAYS.between(puresource, now);
        int daysSinceLastAvrilPo = (int) ChronoUnit.DAYS.between(avril, now);

        return new LastPOsByDistributorDTO(lastUnfiPoDate, daysSinceLastUnfiPo, lastSatauPoDate,
                daysSinceLastSatauPo, lastPuresourcePoDate, daysSinceLastPuresourcePo,
                lastAvrilPoDate, daysSinceLastAvrilPo);
    }

    public List<DashboardFillRateDTO> getRecentFillRates() {

        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();

        List<POSSale> latestYearSales = posSalesRepository.findSalesForYearAndDistributor(currentYear, "unfi");

        OptionalInt lastRecordedMonthOptional = latestYearSales.stream()
                .mapToInt(POSSale::getMonth)
                .max();

        int lastRecordedMonth = lastRecordedMonthOptional.orElse(-1);  // Returns -1 if no data found

        List<POSSale> latestMonthSales = latestYearSales.stream()
                .filter(sale -> sale.getMonth() == lastRecordedMonth)
                .toList();

        List<POSSale> previousMonthSales;

        if (lastRecordedMonth != 1 && lastRecordedMonth != -1) {
            previousMonthSales = latestYearSales.stream()
                    .filter(sale -> sale.getMonth() == lastRecordedMonth - 1)
                    .toList();

        } else if (lastRecordedMonth == 1) {
            previousMonthSales = posSalesRepository.findSalesForYearAndDistributor(currentYear - 1, "unfi")
                    .stream()
                    .filter(sale -> sale.getMonth() == 12)
                    .toList();
        } else {
            throw new RuntimeException("No sales found for");
        }

        Map<Integer, DashboardFillRateDTO> currentMap = new HashMap<>();

        for (POSSale sale : latestMonthSales) {
            int itemNumber = sale.getItemNumber();
            int quantity = sale.getQuantity();
            int orderQuantity = sale.getOrderQuantity();

            if (currentMap.containsKey(itemNumber)) {
                DashboardFillRateDTO existingDTO = currentMap.get(itemNumber);
                existingDTO.setCurrentMonthOrderQuantity(existingDTO.getCurrentMonthOrderQuantity() + orderQuantity);
                existingDTO.setCurrentMonthQuantity(existingDTO.getCurrentMonthQuantity() + quantity);
            } else {
                DashboardFillRateDTO newDTO = new DashboardFillRateDTO();
                newDTO.setItemNumber(itemNumber);
                newDTO.setCurrentMonthOrderQuantity(orderQuantity);
                newDTO.setCurrentMonthQuantity(quantity);
                currentMap.put(itemNumber, newDTO);
            }
        }
        for (POSSale sale : previousMonthSales) {
            int itemNumber = sale.getItemNumber();
            int quantity = sale.getQuantity();
            int orderQuantity = sale.getOrderQuantity();

            if (currentMap.containsKey(itemNumber)) {
                DashboardFillRateDTO existingDTO = currentMap.get(itemNumber);
                existingDTO.setPreviousMonthOrderQuantity(existingDTO.getPreviousMonthOrderQuantity() + orderQuantity);
                existingDTO.setPreviousMonthQuantity(existingDTO.getPreviousMonthQuantity() + quantity);
            } else {
                DashboardFillRateDTO newDTO = new DashboardFillRateDTO();
                newDTO.setItemNumber(itemNumber);
                newDTO.setPreviousMonthOrderQuantity(orderQuantity);
                newDTO.setPreviousMonthQuantity(quantity);
                currentMap.put(itemNumber, newDTO);
            }
        }
        return new ArrayList<>(currentMap.values());
    }

    public List<DashboardDetailedFillRatesDTO> getRecentFillRatesAlerts() {
        return getRecentFillRates().stream()
                .filter(sale -> sale.getPreviousMonthQuantity() - sale.getPreviousMonthOrderQuantity() < 0 ||
                        sale.getCurrentMonthQuantity() - sale.getCurrentMonthOrderQuantity() < 0)
                .map(DashboardDetailedFillRatesDTO::new)
                .toList();
    }

    public McbDTO getMcbInfo() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();

        List<POSSale> sales = posSalesRepository.findSalesForYearsAndDistributor(Arrays.asList(currentYear, currentYear - 1), "unfi");

        double currentYearMcbAmount = 0;
        double currentYearSalesAmount = 0;
        double previousYearMcbAmount = 0;
        double previousYearSalesAmount = 0;

        for (POSSale sale : sales) {
            if (sale.getYear() == currentYear) {
                currentYearMcbAmount += sale.getMcbAmount();
                currentYearSalesAmount += sale.getAmount();
            } else if (sale.getYear() == currentYear - 1) {
                previousYearMcbAmount += sale.getMcbAmount();
                previousYearSalesAmount += sale.getAmount();
            }
        }

        double currentYearMcbPercentage = currentYearSalesAmount != 0
                ? currentYearMcbAmount / currentYearSalesAmount * 100
                : 0;

        double previousYearMcbPercentage = previousYearSalesAmount != 0
                ? previousYearMcbAmount / previousYearSalesAmount * 100
                : 0;

        return new McbDTO(currentYearMcbAmount, currentYearSalesAmount,
                currentYearMcbPercentage, previousYearMcbAmount, previousYearSalesAmount, previousYearMcbPercentage);
    }

    public DetailedMcbDTO getDetailedMcbInfo() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();

        List<POSSale> sales = posSalesRepository.findSalesForYearsAndDistributorAndGroups
                (Arrays.asList(currentYear, currentYear - 1), "unfi", Arrays.asList("SOBEYS QUEBEC", "METRO QUEBEC", "WHOLE FOODS"));

        double currentYearSobeysSales = 0;
        double currentYearSobeysMcb = 0;
        double previousYearSobeysSales = 0;
        double previousYearSobeysMcb = 0;
        double currentYearMetroSales = 0;
        double currentYearMetroMcb = 0;
        double previousYearMetroSales = 0;
        double previousYearMetroMcb = 0;
        double currentYearWfmSales = 0;
        double currentYearWfmMcb = 0;
        double previousYearWfmSales = 0;
        double previousYearWfmMcb = 0;

        for (POSSale sale : sales) {
            if (sale.getYear() == currentYear && sale.getCustomerGroup().equalsIgnoreCase("Sobeys Quebec")) {
                currentYearSobeysSales += sale.getAmount();
                currentYearSobeysMcb += sale.getMcbAmount();
            } else if (sale.getYear() == currentYear - 1 && sale.getCustomerGroup().equalsIgnoreCase("Sobeys Quebec")) {
                previousYearSobeysSales += sale.getAmount();
                previousYearSobeysMcb += sale.getMcbAmount();
            } else if (sale.getYear() == currentYear && sale.getCustomerGroup().equalsIgnoreCase("Metro Quebec")) {
                currentYearMetroSales += sale.getAmount();
                currentYearMetroMcb += sale.getMcbAmount();
            } else if (sale.getYear() == currentYear - 1 && sale.getCustomerGroup().equalsIgnoreCase("Metro Quebec")) {
                previousYearMetroSales += sale.getAmount();
                previousYearMetroMcb += sale.getMcbAmount();
            } else if (sale.getYear() == currentYear && sale.getCustomerGroup().equalsIgnoreCase("WHOLE FOODS")) {
                currentYearWfmSales += sale.getAmount();
                currentYearWfmMcb += sale.getMcbAmount();
            } else if (sale.getYear() == currentYear - 1 && sale.getCustomerGroup().equalsIgnoreCase("WHOLE FOODS")) {
                previousYearWfmSales += sale.getAmount();
                previousYearWfmMcb += sale.getMcbAmount();
            }
        }

        double currentYearSobeysMcbPercentage = currentYearSobeysMcb != 0
                ? currentYearSobeysMcb / currentYearSobeysSales * 100
                : 0;

        double previousYearSobeysMcbPercentage = previousYearSobeysMcb != 0
                ? previousYearSobeysMcb / previousYearSobeysSales * 100
                : 0;

        double currentYearMetroMcbPercentage = currentYearMetroMcb != 0
                ? currentYearMetroMcb / currentYearMetroSales * 100
                : 0;

        double previousYearMetroMcbPercentage = previousYearMetroMcb != 0
                ? previousYearMetroMcb / previousYearMetroSales * 100
                : 0;

        double currentYearWfmMcbPercentage = currentYearWfmMcb != 0
                ? currentYearWfmMcb / currentYearWfmSales * 100
                : 0;

        double previousYearWfmMcbPercentage = previousYearWfmMcb != 0
                ? previousYearWfmMcb / previousYearWfmSales * 100
                : 0;

        return new DetailedMcbDTO(
                currentYearSobeysSales, currentYearSobeysMcb, currentYearSobeysMcbPercentage,
                previousYearSobeysSales, previousYearSobeysMcb, previousYearSobeysMcbPercentage,

                currentYearMetroSales, currentYearMetroMcb, currentYearMetroMcbPercentage,
                previousYearMetroSales, previousYearMetroMcb, previousYearMetroMcbPercentage,

                currentYearWfmSales, currentYearWfmMcb, currentYearWfmMcbPercentage,
                previousYearWfmSales, previousYearWfmMcb, previousYearWfmMcbPercentage
        );
    }
}
