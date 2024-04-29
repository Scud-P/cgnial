package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

     Product findByUnfiCode(String distributorCode);

     Product findByPuresourceCode(String distributorCode);

     Product findBySatauCode(String distributorCode);
}
