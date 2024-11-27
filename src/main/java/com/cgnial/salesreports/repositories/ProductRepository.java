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

    @Query(value = "SELECT p.coutu_code FROM Products p WHERE p.old_puresource_code = :oldPuresourceCode LIMIT 1", nativeQuery = true)
    Integer findCoutuCodeByOldPuresourceCode(@Param("oldPuresourceCode") String oldPuresourceCode);

    @Modifying
    @Query(value = "ALTER TABLE products AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    // Table is products because it references the actual MySQL table, and not the entity, because of nativeQuery = true
    @Query(value = "SELECT p.coutu_code FROM Products p WHERE p.old_satau_code = :oldSatauCode LIMIT 1", nativeQuery = true)
    Integer findCoutuCodeByOldSatauCode(@Param("oldSatauCode") String oldSatauCode);

    @Query("SELECT p.coutuCode FROM Product p WHERE p.oldUnfiCode = :oldUnfiCode")
    Integer findCoutuCodeByOldUnfiCode(@Param("oldUnfiCode") String oldUnfiCode);
}


