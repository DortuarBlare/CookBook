package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.*;
import com.groupproject.nstu.cookbook.entity.request.DishContentRequest;
import com.groupproject.nstu.cookbook.repository.DishContentRepository;
import com.groupproject.nstu.cookbook.repository.DishRepository;
import com.groupproject.nstu.cookbook.service.interfaces.DishContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class DishContentServiceImpl implements DishContentService {

    private final DishContentRepository dishContentRepository;
    private final DishRepository dishRepository;
    private final IngredientServiceImpl ingredientService;

    public DishContentServiceImpl(DishContentRepository dishContentRepository, DishRepository dishRepository, IngredientServiceImpl ingredientService) {
        this.dishContentRepository = dishContentRepository;
        this.dishRepository = dishRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public void createDishContent(DishContentRequest dishContentRequest) {
        DishContent dishContent = new DishContent(
                dishContentRequest.getAmountOfIngredient(),
                dishRepository.getDishByName(dishContentRequest.getDish()).get(),
                ingredientService.findIngredientByName(dishContentRequest.getIngredient()).get()
        );

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
    public List<DishContent> findDishContentByDish(Dish dish) {
        return dishContentRepository.findDishContentByDish(dish);
    }

    @Override
    public ResponseEntity updateDishContent(Long id, DishContentRequest dishContentRequest) {
        try {
            Optional<DishContent> dishContentForUpdate = findDishContentById(id);
            if (dishContentForUpdate.isPresent()) {
                // Обновление количества ингредиента
                dishContentForUpdate.get().setAmountOfIngredient(dishContentRequest.getAmountOfIngredient());

                // Обновление блюда
                Optional<Dish> dishForCheck = dishRepository.getDishByName(dishContentRequest.getDish());
                if (dishForCheck.isPresent())
                    dishContentForUpdate.get().setDish(dishForCheck.get());
                else
                    throw new Exception("Didn't find such dish");

                // Обновление кухни блюда
                Optional<Ingredient> ingredientForCheck = ingredientService.findIngredientByName(dishContentRequest.getIngredient());
                if (ingredientForCheck.isPresent())
                    dishContentForUpdate.get().setIngredient(ingredientForCheck.get());
                else
                    throw new Exception("Didn't find such ingredient");
            } else
                throw new Exception("Didn't find such dish content");

            dishContentRepository.save(dishContentForUpdate.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity deleteDishContent(Long id) {
        try {

            dishContentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
