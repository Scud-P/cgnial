package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.DTO.distributorSalesByGroup.GroupPOSSaleDTO;
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

    @Query("SELECT p FROM POSSale p WHERE LOWER(p.distributor) = LOWER(:distributor) AND LOWER(p.customerGroup) = LOWER(:customerGroup)")
    List<POSSale> findSalesByDistributorAndGroup(@Param("distributor") String distributor, @Param("customerGroup") String customerGroup);

    @Query("SELECT p FROM POSSale p WHERE (p.year) = (:year) AND LOWER(p.distributor) = LOWER(:distributor)")
    List<POSSale> findSalesForYearAndDistributor(@Param("year") int year, @Param("distributor") String distributor);

    @Query("SELECT p FROM POSSale p WHERE p.year IN :years AND p.distributor = :distributor")
    List<POSSale> findSalesForYearsAndDistributor(@Param("years") List<Integer> years, @Param("distributor") String distributor);

    @Query("SELECT p FROM POSSale p WHERE p.year IN :years AND p.distributor = :distributor AND p.customerGroup IN :groups")
    List<POSSale> findSalesForYearsAndDistributorAndGroups(@Param("years") List<Integer> years, @Param("distributor") String distributor, @Param("groups") List<String> groups);

}
