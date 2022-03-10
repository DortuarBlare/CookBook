package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishType;
import com.groupproject.nstu.cookbook.repository.DishRepository;
import com.groupproject.nstu.cookbook.service.interfaces.DishService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishTypeServiceImpl dishTypeService;
    private final CuisineServiceImpl cuisineService;

    public DishServiceImpl(DishRepository dishRepository, DishTypeServiceImpl dishTypeService, CuisineServiceImpl cuisineService) {
        this.dishRepository = dishRepository;
        this.dishTypeService = dishTypeService;
        this.cuisineService = cuisineService;
    }

    @Override
    public Optional<Dish> findDishById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public void createDish(Dish dish) {
        dishRepository.save(dish);
    }

    @Override
    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    @Override
    public Optional<Dish> findDishByName(String name) {
        return dishRepository.getDishByName(name);
    }

    @Override
    public List<Dish> findDishByNames(String names) {
        Specification<Dish> specification = (root, criteriaQuery, criteriaBuilder) -> {
            String[] splitNames = names.split(" ");
            List<Predicate> predicates = new ArrayList<Predicate>();

            for (String splitName : splitNames) {
                predicates.add(criteriaBuilder.equal(root.<String>get("name"), splitName));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        };

        return dishRepository.findAll(specification);
    }

    @Override
    public ResponseEntity updateDish(Long id, Dish newDish) {
        try {
            Optional<Dish> dish = findDishById(id);
            if (dish.isPresent())
            {
                // Обновление названия блюда
                Optional<Dish> dishForConstraintCheck = findDishByName(newDish.getName());
                if (dishForConstraintCheck.isPresent())
                    throw new SQLException("This dish already exist");
                else
                    dish.get().setName(newDish.getName());

                // Обновление описания приготовления
                dish.get().setCookingDescription(newDish.getCookingDescription());

                // Обновление типа блюда
                Optional<DishType> dishTypeForCheck = dishTypeService.findDishTypeById(newDish.getDishType().getId());
                if (dishTypeForCheck.isPresent())
                    dish.get().setDishType(dishTypeForCheck.get());
                else
                    throw new Exception("Didn't find such dish type");

                // Обновление кухни блюда
                Optional<Cuisine> cuisineForCheck = cuisineService.findCuisineById(newDish.getDishCuisine().getId());
                if (cuisineForCheck.isPresent())
                    dish.get().setDishCuisine(cuisineForCheck.get());
                else
                    throw new Exception("Didn't find such cuisine");
            }
            else
                throw new Exception("Didn't find such ingredient");

            dishRepository.save(dish.get());
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
    public ResponseEntity deleteDish(Long id) {
        try {
            dishRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
