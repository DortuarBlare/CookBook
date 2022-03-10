package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.service.DishServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groupproject.nstu.cookbook.entity.response.DishResponse;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Dish")
@RequestMapping("/dish/")
public class DishController {

    private final DishServiceImpl dishService;

    public DishController(DishServiceImpl dishService) {
        this.dishService = dishService;
    }

    @PostMapping("/createDish")
    public void createDish(@RequestBody Dish dish){
        dishService.createDish(dish);
    }

    @GetMapping("/getAll")
    public List<Dish> getAllDish(){
        return dishService.getAll();
    }

    @GetMapping("/findByAllFilters")
    public List<DishResponse> findDishListByAllFilters(String ingredients, String dishType, String cuisines) {
        return dishService.findDish(ingredients, dishType, cuisines);
    }

    @GetMapping("/find/{id}")
    public Optional<Dish> findDishById(@PathVariable Long id){
        return dishService.findDishById(id);
    }

    @GetMapping("/findByName/{name}")
    public Optional<Dish> findDishByName(@PathVariable String name) {
        return dishService.findDishByName(name);
    }

    @GetMapping("/findByNames")
    public List<Dish> findDishByNames(String names) {
        return dishService.findDishByNames(names);
    }

    @GetMapping("/findByCuisinesAndDishTypes")
    public List<Dish> findDishByCuisinesAndDishTypes(String cuisines, String dishType) {
        return dishService.findDishByCuisinesAndDishType(cuisines, dishType);
    }

    @PutMapping("/updateDish/{id}")
    public ResponseEntity updateDish(@PathVariable Long id, @RequestBody Dish dish) {
        return dishService.updateDish(id, dish);
    }

    @DeleteMapping("/deleteDish/{id}")
    public ResponseEntity deleteDish(@PathVariable Long id) {
        return dishService.deleteDish(id);
    }
}
