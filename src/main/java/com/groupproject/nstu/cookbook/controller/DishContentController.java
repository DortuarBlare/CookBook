package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.DishContent;
import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.service.DishContentServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Dish Content")
@RequestMapping("/dishContent/")
public class DishContentController {

    private final DishContentServiceImpl dishContentService;

    public DishContentController(DishContentServiceImpl dishContentService) {
        this.dishContentService = dishContentService;
    }

    @GetMapping("/find/{id}")
    public Optional<DishContent> findDishContentById(@PathVariable Long id){
        return dishContentService.findDishContentById(id);
    }

    @GetMapping("/getAll")
    public List<DishContent> getAllDishContents(){
        return dishContentService.getAll();
    }


    @PostMapping("/createDishContent")
    public void createDishContent(@RequestBody DishContent dishContent){
        dishContentService.createDishContent(dishContent);
    }
}
