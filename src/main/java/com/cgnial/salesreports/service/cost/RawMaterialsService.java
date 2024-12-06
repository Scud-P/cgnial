package com.cgnial.salesreports.service.cost;

import com.cgnial.salesreports.domain.RawMaterial;
import com.cgnial.salesreports.repositories.RawMaterialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RawMaterialsService {

    @Autowired
    private RawMaterialsRepository rawMaterialsRepository;

    public RawMaterial addRawMaterial(RawMaterial rawMaterial) {
        return rawMaterialsRepository.save(rawMaterial);
    }

    public void deleteRawMaterial(String id) {
        rawMaterialsRepository.deleteById(id);
    }

    public RawMaterial editRawMaterial(RawMaterial rawMaterial) {

        RawMaterial materialToUpdate = rawMaterialsRepository.findById(rawMaterial.getId())
                .orElseThrow( () -> new IllegalArgumentException("Product not found for id: " + rawMaterial.getId()));

        materialToUpdate.setDescription(rawMaterial.getDescription());
        materialToUpdate.setYield(rawMaterial.getYield());
        materialToUpdate.setCost(rawMaterial.getCost());
        return rawMaterialsRepository.save(materialToUpdate);
    }

}
