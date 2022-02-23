package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.repository.DishRepository;
import com.groupproject.nstu.cookbook.service.interfaces.DishService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
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
}
