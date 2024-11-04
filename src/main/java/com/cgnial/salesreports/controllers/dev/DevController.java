package com.cgnial.salesreports.controllers.dev;

import com.cgnial.salesreports.service.POSSalesService;
import com.cgnial.salesreports.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/dev")
@RestController
public class DevController {

    @Autowired
    private POSSalesService posSalesService;

    @Autowired
    private ProductService productService;


    @DeleteMapping("/clear")
    private ResponseEntity<String> clearEverything() {
        posSalesService.clearAllSales();
        posSalesService.resetAutoIncrement();
        productService.deleteAll();
        productService.resetAutoIncrement();
        return ResponseEntity.ok("All databases cleared and autoincrement reset");
    }


}
