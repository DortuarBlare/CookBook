package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishContent;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DishContentService {

    void createDishContent(DishContent dishContent);

    Optional<DishContent> findDishContentById(Long id);

    List<DishContent> getAll();

    List<DishContent> findDishContentByDish(Dish dish);

    ResponseEntity updateDishContent(Long id, DishContent newDishContent);

    ResponseEntity deleteDishContent(Long id);
}
