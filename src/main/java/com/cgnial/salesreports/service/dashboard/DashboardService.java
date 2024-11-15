package com.cgnial.salesreports.service.dashboard;

import com.cgnial.salesreports.domain.DTO.dashboard.DashboardToDistributorSalesSummaryDTO;
import com.cgnial.salesreports.domain.DTO.dashboard.PurchaseOrderDashboardDTO;
import com.cgnial.salesreports.domain.DTO.dashboard.DashboardYTDSalesDTO;
import com.cgnial.salesreports.domain.PurchaseOrder;
import com.cgnial.salesreports.repositories.PurchaseOrderRepository;
import com.cgnial.salesreports.util.DatesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private PurchaseOrderRepository poRepository;

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

        LocalDate oneYearAgo = LocalDate.of(now.getYear()-1, now.getMonthValue(), now.getDayOfMonth());

        //TODO ATTENTION IL FAUT REFACTO POUR LE 29 FÉVRIER DES ANNÉES BISSEXTILES

        double totalAmountForLastYear = dtos.stream()
                .filter(sale -> sale.getPoDate().isBefore(oneYearAgo))
                .filter(sale -> sale.getPoDate().getYear() == oneYearAgo.getYear())
                .mapToDouble(PurchaseOrderDashboardDTO::getAmount)
                .sum();

        purchaseOrderSummaryDTO.setTotalAmount(totalAmountForLastYear);

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
}
