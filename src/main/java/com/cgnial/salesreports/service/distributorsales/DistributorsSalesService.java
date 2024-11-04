package com.cgnial.salesreports.service.distributorsales;

import com.cgnial.salesreports.domain.DTO.AccountPOSSaleDTO;
import com.cgnial.salesreports.domain.DTO.cases.POSSaleDTO;
import com.cgnial.salesreports.domain.DTO.distributorSales.QuarterlyDistributorSalesDTO;
import com.cgnial.salesreports.domain.DTO.distributorSales.YearlyDistributorSalesDTO;
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
}
