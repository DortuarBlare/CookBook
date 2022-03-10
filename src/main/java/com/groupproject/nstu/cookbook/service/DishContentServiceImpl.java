package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishContent;
import com.groupproject.nstu.cookbook.repository.DishContentRepository;
import com.groupproject.nstu.cookbook.service.interfaces.DishContentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishContentServiceImpl implements DishContentService {

    private final DishContentRepository dishContentRepository;

    public DishContentServiceImpl(DishContentRepository dishContentRepository) {
        this.dishContentRepository = dishContentRepository;
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
    public List<DishContent> findDishContentByDish(Dish dish) {
        return dishContentRepository.findDishContentByDish(dish);
    }


}
