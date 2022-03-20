package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.response.DishRequest;
import com.groupproject.nstu.cookbook.service.DishServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groupproject.nstu.cookbook.entity.response.DishResponse;

import java.sql.SQLException;
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
    public ResponseEntity createDish(@RequestBody DishRequest dishRequest){
        return dishService.createDish(dishRequest);
    }

    @GetMapping("/getAllDishResponse")
    public List<DishResponse> getAllDishResponse(){
        return dishService.getAllDishResponse();
    }

    @GetMapping("/getAllDish")
    public List<Dish> getAllDish(){
        return dishService.getAllDish();
    }

    @GetMapping("/findByAllFilters/{ingredients}/{dishType}/{cuisines}")
    public List<DishResponse> findDishListByAllFilters(String ingredients, String dishType, String cuisines) {
        return dishService.findDishByAllFilters(ingredients, dishType, cuisines);
    }

    @GetMapping("/findById/{id}")
    public Optional<Dish> findDishById(@PathVariable Long id){
        return dishService.findDishById(id);
    }

    @GetMapping("/findByName/{name}")
    public List<DishResponse> findDishResponseByName(@PathVariable String name) {
        return dishService.findDishResponseByName(name);
    }

    @GetMapping("/findByNames/{names}")
    public List<Dish> findDishByNames(String names) {
        return dishService.findDishByNames(names);
    }

    @GetMapping("/findByCuisinesAndDishType/{cuisines}/{dishType}")
    public List<Dish> findDishByCuisinesAndDishType(String cuisines, String dishType) {
        return dishService.findDishByCuisinesAndDishType(cuisines, dishType);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity updateDish(@PathVariable Long id, @RequestBody Dish dish) {
        return dishService.updateDish(id, dish);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity deleteDish(@PathVariable Long id) {
        return dishService.deleteDish(id);
    }
}