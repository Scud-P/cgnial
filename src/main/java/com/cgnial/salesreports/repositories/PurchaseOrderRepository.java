package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    @Modifying
    @Query(value = "ALTER TABLE purchase_orders AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
