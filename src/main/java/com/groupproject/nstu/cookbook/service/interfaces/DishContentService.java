package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.DishContent;
import com.groupproject.nstu.cookbook.entity.Ingredient;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DishContentService {

    void createDishContent(DishContent dishContent);

    Optional<DishContent> findDishContentById(Long id);

    List<DishContent> getAll();

    List<Ingredient> getAllWithIngredientList();

//    List<DishContent> findDishContentByIngredients(String ingredients);
}
