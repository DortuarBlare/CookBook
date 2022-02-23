package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.Dish;

import java.util.List;
import java.util.Optional;

public interface DishService {

    Optional<Dish> findDishById(Long id);

    void createDish(Dish dish);

    List<Dish> getAll();

}
