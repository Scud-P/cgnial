package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.RawMaterial;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialsRepository extends MongoRepository<RawMaterial, String> {

}
