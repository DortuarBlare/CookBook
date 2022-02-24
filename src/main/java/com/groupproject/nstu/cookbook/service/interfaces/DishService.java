package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishType;

import java.util.List;
import java.util.Optional;

public interface DishService {

    Optional<Dish> findDishById(Long id);

    void createDish(Dish dish);

    List<Dish> getAll();

    Optional<Dish> findDishByName(String name);


}
