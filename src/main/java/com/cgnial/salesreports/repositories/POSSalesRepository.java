package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.POSSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface POSSalesRepository extends JpaRepository<POSSale, Long> {

    @Modifying
    @Query("DELETE FROM POSSale p WHERE LOWER(p.distributor) = LOWER(:distributor)")
    void deleteAllByDistributorIgnoreCase(@Param("distributor") String distributor);

    @Modifying
    @Query(value = "ALTER TABLE pos_sales AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    @Query("SELECT p FROM POSSale p WHERE LOWER(p.distributor) = LOWER(:distributor)")
    List<POSSale> findByDistributor(@Param("distributor") String distributor);

    @Query("SELECT p FROM POSSale p WHERE LOWER(p.distributor) = LOWER(:distributor) AND p.quarter <= (:quarter)")
    List<POSSale> findByDistributorInferiorOrEqualToQuarter(@Param("distributor") String distributor, @Param("quarter") int quarter);

}
