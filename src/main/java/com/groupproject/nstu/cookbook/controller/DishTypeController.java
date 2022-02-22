package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.DishType;
import com.groupproject.nstu.cookbook.service.DishTypeServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create")
    public void createDishType(@RequestBody DishType dishType) {
        dishTypeService.createDishType(dishType);
    }

    @GetMapping("/getAll")
    public List<DishType> getAllDishType() {
        return dishTypeService.getAll();
    }

    @GetMapping("/find/{id}")
    public Optional<DishType> findDishTypeById(@PathVariable Long id){
        return dishTypeService.findDishTypeById(id);
    }
}
