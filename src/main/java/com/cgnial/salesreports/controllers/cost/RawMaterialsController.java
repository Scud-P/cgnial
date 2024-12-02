package com.cgnial.salesreports.controllers.cost;

import com.cgnial.salesreports.domain.RawMaterial;
import com.cgnial.salesreports.service.cost.RawMaterialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rawMaterials")
public class RawMaterialsController {

    private static final Logger logger = LoggerFactory.getLogger(RawMaterialsController.class);


    @Autowired
    private RawMaterialsService rawMaterialsService;

    @PostMapping("/add")
    public ResponseEntity<RawMaterial> validateRawMaterial(@RequestBody RawMaterial rawMaterial) {
        logger.info("Passing raw material to service layer: {}", rawMaterial);
        rawMaterialsService.addRawMaterial(rawMaterial);
        return ResponseEntity.ok(rawMaterial);
    }

    @PutMapping("/edit")
    public ResponseEntity<RawMaterial> editRawMaterial(@RequestBody RawMaterial rawMaterial) {
        logger.info("Passing raw material to service layer for edit: {}", rawMaterial);
        rawMaterialsService.editRawMaterial(rawMaterial);
        return ResponseEntity.ok(rawMaterial);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRawMaterial(@PathVariable("id") String id) {
        logger.info("Attempting to delete raw material with id: {}", id);
        rawMaterialsService.deleteRawMaterial(id);
        return ResponseEntity.ok(id);
    }

}
