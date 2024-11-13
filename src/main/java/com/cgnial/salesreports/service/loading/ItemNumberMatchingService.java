package com.cgnial.salesreports.service.loading;

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

    public Integer determineProductCodeFromOldUnfiItemNumber(String unfiItemNumber) {
        Integer coutuCode = productRepository.findCoutuCodeByOldUnfiCode(unfiItemNumber);
        if (coutuCode == null ) {
            logger.info("Could not find Coutu code for UNFI item number: {}", unfiItemNumber);
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

    public int determineProductCodeFromOldSatauItemNumber(String satauItemNumber) {
        // Check if satauItemNumber is null or empty (after trimming)
        if (satauItemNumber == null || satauItemNumber.trim().isEmpty()) {
            // Log the issue and return an indication that processing should not continue
            logger.info("Satau item number is null or empty, skipping processing.");
            return 0; // Return a special value to indicate no product code was found
        }

        // Proceed with the query if the item number is valid
        Integer coutuCode = productRepository.findCoutuCodeByOldSatauCode(satauItemNumber);

        // Return 0 if no code was found, or the valid coutuCode otherwise
        return (coutuCode != null) ? coutuCode : 0;
    }

}
