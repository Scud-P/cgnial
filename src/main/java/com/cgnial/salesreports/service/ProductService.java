package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.Product;
import com.cgnial.salesreports.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String getCoutuCode(String distributor, String distributorCode) {

        String coutuCode = "";
        Product product;

        switch (distributor) {
            case "unfi" -> {
                product = productRepository.findByUnfiCode(distributorCode);
                coutuCode = product.getCoutuCode();
            }
            case "puresource" -> {
                product = productRepository.findByPuresourceCode(distributorCode);
                coutuCode = product.getCoutuCode();
            }
            case "satau" -> {
                product = productRepository.findBySatauCode(distributorCode);
                coutuCode = product.getCoutuCode();
            }
        }
        return coutuCode;
    }
}
