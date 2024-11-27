package com.cgnial.salesreports.controllers.reportUpdates;


import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.service.updating.UpdaterService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/update")
@RestController
public class SalesReportUpdateController {

    @Autowired
    private UpdaterService salesService;

    @PostMapping("/satauSales")
    public ResponseEntity<List<POSSale>> updateSatauSales() throws IOException {
        List<POSSale> satauSales = salesService.loadNewSatauSales();
        return ResponseEntity.ok(satauSales);
    }

    @PostMapping("/unfiSales")
    public ResponseEntity<List<POSSale>> updateUnfiSales() throws IOException {
        List<POSSale> unfiSales = salesService.loadNewUnfiSales();
        return ResponseEntity.ok(unfiSales);
    }

    @PostMapping("/puresourceSales")
    public ResponseEntity<List<POSSale>> updatePuresourceSales() throws Exception {
        List<POSSale> puresourceSales = salesService.loadNewPuresourceSales();
        return ResponseEntity.ok(puresourceSales);
    }
}
