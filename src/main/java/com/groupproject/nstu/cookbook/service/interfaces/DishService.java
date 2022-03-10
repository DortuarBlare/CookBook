package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishContent;
import com.groupproject.nstu.cookbook.entity.response.DishResponse;

import java.util.List;
import java.util.Optional;

public interface DishService {

    Optional<Dish> findDishById(Long id);

    void createDish(Dish dish);

    List<Dish> getAll();

    Optional<Dish> findDishByName(String name);

    List<Dish> findDishByNames(String names);

    List<DishResponse> findDish(String ingredients);

}
