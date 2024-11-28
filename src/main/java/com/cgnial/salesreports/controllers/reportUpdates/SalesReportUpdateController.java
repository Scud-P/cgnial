package com.cgnial.salesreports.controllers.reportUpdates;


import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.service.updating.UpdaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/update")
@RestController
public class SalesReportUpdateController {

    @Autowired
    private UpdaterService salesService;

    private static final Logger logger = LoggerFactory.getLogger(SalesReportUpdateController.class);


    @PostMapping("/satauSales")
    public ResponseEntity<List<POSSale>> updateSatauSales(@RequestParam("file")MultipartFile file) throws IOException {
        List<POSSale> satauSales = salesService.loadNewSatauSales(file);
        logger.info("Endpoint received a post request");
        return ResponseEntity.ok(satauSales);
    }

    @PostMapping("/unfiSales")
    public ResponseEntity<List<POSSale>> updateUnfiSales(@RequestParam("file")MultipartFile file) throws IOException {
        List<POSSale> unfiSales = salesService.loadNewUnfiSales(file);
        return ResponseEntity.ok(unfiSales);
    }

    @PostMapping("/puresourceSales")
    public ResponseEntity<List<POSSale>> updatePuresourceSales(@RequestParam("file")MultipartFile file) throws Exception {
        List<POSSale> puresourceSales = salesService.loadNewPuresourceSales(file);
        return ResponseEntity.ok(puresourceSales);
    }
}
