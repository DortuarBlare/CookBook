package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.DishType;
import com.groupproject.nstu.cookbook.entity.request.DishTypeRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DishTypeService {

    Optional<DishType> findDishTypeById(Long id);

    void createDishType(DishTypeRequest dishTypeRequest);

    List<DishType> getAll();

    Optional<DishType> findDishTypeByName(String name);

    List<DishType> findDishTypeByNames(String names);

    ResponseEntity updateDishType(Long id, DishTypeRequest dishTypeRequest);

    ResponseEntity deleteDishType(Long id);
}
