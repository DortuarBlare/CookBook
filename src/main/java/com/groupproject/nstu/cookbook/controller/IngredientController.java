package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.service.IngredientServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Ingredients")
@RequestMapping("/ingredients/")
public class IngredientController {

    private final IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/createIngredient")
    public void createIngredient(@RequestBody Ingredient ingredient){
        ingredientService.createIngredient(ingredient);
    }

    @GetMapping("/find/{id}")
    public Optional<Ingredient> findIngredientById(@PathVariable Long id){
        return ingredientService.findIngredientById(id);
    }

    @GetMapping("/getAll")
    public List<Ingredient> getAllIngredients(){
        return ingredientService.getAll();
    }

    @GetMapping("/findByName/{name}")
    public Optional<Ingredient> findIngredientByName(@PathVariable String name) {
        return ingredientService.findIngredientByName(name);
    }

}
