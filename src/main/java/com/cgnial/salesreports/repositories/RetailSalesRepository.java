package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.RetailSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailSalesRepository extends JpaRepository<RetailSale, Integer> {

}
