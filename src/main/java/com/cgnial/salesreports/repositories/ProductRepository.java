package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p.coutuCode FROM Product p WHERE p.puresourceCode = :puresourceCode")
    Integer findCoutuCodeByPuresourceCode(@Param("puresourceCode") String puresourceCode);

    int findCoutuCodeByUnfiCode(String puresourceCode);

    @Query("SELECT p.coutuCode FROM Product p WHERE p.satauCode = :satauCode")
    Integer findCoutuCodeBySatauCode(String satauCode);
}


