package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.service.CuisineServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Cuisine")
@RequestMapping("/cuisine/")
public class CuisineController {

    private final CuisineServiceImpl cuisineService;

    public CuisineController(CuisineServiceImpl cuisineService) {
        this.cuisineService = cuisineService;
    }

    @PostMapping("/create")
    public void createCuisine(@RequestBody Cuisine cuisine) {
        cuisineService.createCuisine(cuisine);
    }

    @GetMapping("/getAll")
    public List<Cuisine> getAllCuisine() {
        return cuisineService.getAll();
    }

    @GetMapping("/find/{id}")
    public Optional<Cuisine> findCuisineById(@PathVariable Long id){
        return cuisineService.findCuisineById(id);
    }
}
