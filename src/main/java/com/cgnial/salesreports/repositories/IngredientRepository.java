package com.cgnial.salesreports.repositories;

import com.cgnial.salesreports.domain.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, String> {

}
