package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.domain.parameter.PuresourcePOSParameter;
import com.cgnial.salesreports.repositories.POSSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class POSSalesService {

    @Autowired
    private POSSalesRepository posSalesRepository;

    @Transactional
    public void loadAllPuresourceSales(List<PuresourcePOSParameter> sales) {
        List<POSSale> salesToPersist = sales.stream()
                .map(parameter -> new POSSale(parameter))
                .toList();
        posSalesRepository.saveAll(salesToPersist);
    }

    @Transactional
    public void clearAllPuresourceSales() {
        posSalesRepository.deleteAllByDistributorIgnoreCase("Puresource");
        posSalesRepository.resetAutoIncrement();
    }

}
