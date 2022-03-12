package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.response.DishResponse;
import com.groupproject.nstu.cookbook.service.DishServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController(value = "/user")
@Tag(name = "User")
@RequestMapping("/user/dish/")
public class UserController {
    private final DishServiceImpl dishService;

    public UserController(DishServiceImpl dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/getAll")
    public List<Dish> getAllDish(){
        return dishService.getAll();
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
    public Optional<DishResponse> findDishResponseByName(@PathVariable String name) {
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
}
