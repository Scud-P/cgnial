package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    @Modifying
    @Query(value = "ALTER TABLE purchase_orders AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    @Query(value = "SELECT p.po_date FROM purchase_orders p WHERE LOWER(p.distributor) = LOWER(:distributor) ORDER BY p.id DESC LIMIT 1", nativeQuery = true)
    String findLastOrderDateForDistributor(@Param("distributor") String distributor);

    @Query("SELECT MAX(p.id) FROM PurchaseOrder p WHERE p.distributor = :distributor")
    int findLastSaleIdForDistributor(@Param("distributor") String distributor);

    @Query("SELECT p FROM PurchaseOrder p WHERE LOWER(p.distributor) = LOWER(:distributor)")
    List<PurchaseOrder> findByDistributor(@Param("distributor") String distributor);

    PurchaseOrder findTopByOrderByIdDesc();
}
