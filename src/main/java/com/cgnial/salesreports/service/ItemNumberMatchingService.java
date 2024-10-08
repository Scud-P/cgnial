package com.cgnial.salesreports.service;

import com.cgnial.salesreports.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemNumberMatchingService {

    private static final Logger logger = LoggerFactory.getLogger(ItemNumberMatchingService.class);


    @Autowired
    private ProductRepository productRepository;

    public Integer determineProductCodeFromPuresourceItemNumber(String puresourceItemNumber) {
        Integer coutuCode = productRepository.findCoutuCodeByPuresourceCode(puresourceItemNumber);
        if (coutuCode == null ) {
            return 0;
        }
        return  coutuCode;
    }

    public Integer determineProductCodeFromOldPuresourceItemNumber(String puresourceItemNumber) {
        Integer coutuCode = productRepository.findCoutuCodeByOldPuresourceCode(puresourceItemNumber);
        if (coutuCode == null ) {
            return 0;
        }
        return  coutuCode;
    }

    public Integer determineProductCodeFromUnfiItemNumber(String unfiItemNumber) {
        Integer coutuCode = productRepository.findCoutuCodeByUnfiCode(unfiItemNumber);
        if (coutuCode == null ) {
            return 0;
        }
        return  coutuCode;
    }

    public int determineProductCodeFromSatauItemNumber(String satauItemNumber) {
        Integer coutuCode = productRepository.findCoutuCodeBySatauCode(satauItemNumber);
        if (coutuCode == null ) {
            return 0;
        }
        return  coutuCode;
    }
}
