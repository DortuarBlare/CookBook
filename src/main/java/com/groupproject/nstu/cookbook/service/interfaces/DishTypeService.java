package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.DishType;

import java.util.List;
import java.util.Optional;

public interface DishTypeService {

    Optional<DishType> findDishTypeById(Long id);

    void createDishType(DishType dishType);

    List<DishType> getAll();

}
