package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishContent;
import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.repository.DishContentRepository;
import com.groupproject.nstu.cookbook.service.interfaces.DishContentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DishContentServiceImpl implements DishContentService {

    private final DishContentRepository dishContentRepository;
    private final IngredientServiceImpl ingredientService;
    private final DishServiceImpl dishService;

    public DishContentServiceImpl(DishContentRepository dishContentRepository, IngredientServiceImpl ingredientService, DishServiceImpl dishService) {
        this.dishContentRepository = dishContentRepository;
        this.ingredientService = ingredientService;
        this.dishService = dishService;
    }

    @Override
    public void createDishContent(DishContent dishContent) {
        dishContentRepository.save(dishContent);
    }

    @Override
    public Optional<DishContent> findDishContentById(Long id) {
        return dishContentRepository.findById(id);
    }

    @Override
    public List<DishContent> getAll() {
        return dishContentRepository.findAll();
    }

    @Override
    public List<DishContent> findDishContentByIngredients(String ingredients) {

        List<Ingredient> ingredientList = ingredientService.findIngredientByNames(ingredients);

        List<Dish> dishList = dishService.getAll();

        List<DishContent> resultList = new ArrayList();

        for (Dish dish: dishList){

            int amountOfMatchedIngredients = 0;

            for (DishContent dishContent: dishContentRepository.findDishContentByDish(dish)){

                for (Ingredient ingredient: ingredientList){

                    if(ingredient.getId() == dishContent.getIngredient().getId()){
                        amountOfMatchedIngredients++;
                    }

                }

            }

            if(amountOfMatchedIngredients==ingredientList.size()){
                resultList.addAll(dishContentRepository.findDishContentByDish(dish));
            }

        }

        return resultList;

    }

}
