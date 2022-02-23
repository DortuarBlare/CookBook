package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.service.DishServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/find/{id}")
    public Optional<Dish> findDishById(@PathVariable Long id){
        return dishService.findDishById(id);
    }
}
