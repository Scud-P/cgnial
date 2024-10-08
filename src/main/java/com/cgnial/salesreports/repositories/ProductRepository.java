package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p.coutuCode FROM Product p WHERE p.puresourceCode = :puresourceCode")
    Integer findCoutuCodeByPuresourceCode(@Param("puresourceCode") String puresourceCode);

    @Query("SELECT p.coutuCode FROM Product p WHERE p.unfiCode = :unfiCode")
    Integer findCoutuCodeByUnfiCode(@Param("unfiCode") String unfiCode);

    @Query("SELECT p.coutuCode FROM Product p WHERE p.satauCode = :satauCode")
    Integer findCoutuCodeBySatauCode(@Param("satauCode") String satauCode);

    @Query("SELECT p.coutuCode FROM Product p WHERE p.oldPuresourceCode = :oldPuresourceCode")
    Integer findCoutuCodeByOldPuresourceCode(@Param("oldPuresourceCode") String oldPuresourceCode);

    @Modifying
    @Query(value = "ALTER TABLE products AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}


