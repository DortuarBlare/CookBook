package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.repository.IngredientRepository;
import com.groupproject.nstu.cookbook.service.interfaces.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Optional<Ingredient> findIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public void createIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> findIngredientByName(String name) {
        return ingredientRepository.getIngredientByName(name);
    }
}
