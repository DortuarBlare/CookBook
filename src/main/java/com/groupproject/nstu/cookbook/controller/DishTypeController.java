package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.DishType;
import com.groupproject.nstu.cookbook.service.DishTypeServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Dish Type")
@RequestMapping("/dishType/")
public class DishTypeController {

    private final DishTypeServiceImpl dishTypeService;

    public DishTypeController(DishTypeServiceImpl dishTypeService) {
        this.dishTypeService = dishTypeService;
    }

    @PostMapping("/createDishType")
    public void createDishType(@RequestBody DishType dishType) {
        dishTypeService.createDishType(dishType);
    }

    @GetMapping("/getAll")
    public List<DishType> getAllDishType() {
        return dishTypeService.getAll();
    }

    @GetMapping("/find/{id}")
    public Optional<DishType> findDishTypeById(@PathVariable Long id) {
        return dishTypeService.findDishTypeById(id);
    }

    @GetMapping("/findByName/{name}")
    public Optional<DishType> findDishTypeByName(@PathVariable String name) {
        return dishTypeService.findDishTypeByName(name);
    }

    @GetMapping("/findByNames")
    public List<DishType> findDishTypeByNames(String names) {
        return dishTypeService.findDishTypeByNames(names);
    }

    @PutMapping("/updateCuisine/{id}")
    public ResponseEntity updateDishType(@PathVariable Long id, @RequestBody DishType dishType) {
        return dishTypeService.updateDishType(id, dishType);
    }

    @DeleteMapping("/deleteCuisine/{id}")
    public ResponseEntity deleteDishType(@PathVariable Long id) {
        return dishTypeService.deleteDishType(id);
    }
}
