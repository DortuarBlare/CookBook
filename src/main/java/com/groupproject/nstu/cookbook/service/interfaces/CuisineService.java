package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.request.CuisineRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CuisineService {

    void createCuisine(CuisineRequest cuisineRequest);

    List<Cuisine> getAll();

    Optional<Cuisine> findCuisineById(Long id);

    Optional<Cuisine> findCuisineByName(String name);

    List<Cuisine> findCuisineByNames(String names);

    ResponseEntity updateCuisine(Long id, CuisineRequest cuisineRequest);

    ResponseEntity deleteCuisine(Long id);

}
