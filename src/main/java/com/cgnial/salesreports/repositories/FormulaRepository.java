package com.cgnial.salesreports.repositories;


import com.cgnial.salesreports.domain.Formula;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormulaRepository extends MongoRepository<Formula, String> {


}
