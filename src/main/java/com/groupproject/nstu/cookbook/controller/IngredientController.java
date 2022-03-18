package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.entity.request.IngredientRequest;
import com.groupproject.nstu.cookbook.service.IngredientServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Ingredient")
@RequestMapping("/ingredient/")
public class IngredientController {

    private final IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/createIngredient")
    public void createIngredient(@RequestBody IngredientRequest ingredientRequest){
        ingredientService.createIngredient(ingredientRequest);
    }

    @GetMapping("/findById/{id}")
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

    @GetMapping("/findByNames/{names}")
    public List<Ingredient> findIngredientByNames(@PathVariable String names) {
        return ingredientService.findIngredientByNames(names);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity updateIngredient(@PathVariable Long id, @RequestBody IngredientRequest ingredientRequest) {
        return ingredientService.updateIngredient(id, ingredientRequest);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity deleteIngredient(@PathVariable Long id) {
        return ingredientService.deleteIngredient(id);
    }
}
