package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.POSSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface POSSalesRepository extends JpaRepository<POSSale, Long> {

    @Modifying
    @Query("DELETE FROM POSSale p WHERE LOWER(p.distributor) = LOWER(:distributor)")
    void deleteAllByDistributorIgnoreCase(@Param("distributor") String distributor);

}
