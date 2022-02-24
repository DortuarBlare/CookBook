package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {

    Optional<Ingredient> findIngredientById(Long id);

    void createIngredient(Ingredient ingredient);

    List<Ingredient> getAll();

    Optional<Ingredient> findIngredientByName(String name);

    List<Ingredient> findIngredientByNames(String names);
}
