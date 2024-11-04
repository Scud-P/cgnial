package com.cgnial.salesreports.controllers.toDistributors;

import com.cgnial.salesreports.domain.DTO.cases.CasesByDistributorByYearDTO;
import com.cgnial.salesreports.domain.DTO.cases.CasesPerDistributorDTO;
import com.cgnial.salesreports.service.toDistributors.DistributorCasesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/distributorCases")
@CrossOrigin(origins = "http://localhost:3000")
public class DistributorCasesController {

    private static final Logger logger = LoggerFactory.getLogger(DistributorCasesController.class);

    @Autowired
    private DistributorCasesService distributorCasesService;

    @GetMapping("/byDistributorByYear")
    public ResponseEntity<List<CasesByDistributorByYearDTO>> salesByDistributorByYear() {
        logger.info("Calling service...");
        List<CasesByDistributorByYearDTO> cases = distributorCasesService.getCasesByDistributorByYearDTO();
        return ResponseEntity.ok(cases);
    }

    @GetMapping("/byDistributorByYearByQuarter")
    public ResponseEntity<List<CasesPerDistributorDTO>> salesByDistributorByYearByQuarter() {
        List<CasesPerDistributorDTO> cases = distributorCasesService.getCasesByDistributorByYearAndQuarter();
        return ResponseEntity.ok(cases);
    }

}
