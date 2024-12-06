package com.cgnial.salesreports.service.cost;

import com.cgnial.salesreports.domain.Ingredient;
import com.cgnial.salesreports.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> saveAllIngredients(List<Ingredient> ingredients) {
        return ingredientRepository.saveAll(ingredients);
    }

    public void deleteIngredientById(String id) {
        Ingredient ingredientToDelete = ingredientRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Ingredient with id " + id + " not found in DB"));
        ingredientRepository.deleteById(id);
    }

}
