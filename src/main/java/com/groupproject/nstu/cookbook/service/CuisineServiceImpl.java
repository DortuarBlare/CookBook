package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.repository.CuisineRepository;
import com.groupproject.nstu.cookbook.service.interfaces.CuisineService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuisineServiceImpl implements CuisineService {

    private final CuisineRepository cuisineRepository;

    public CuisineServiceImpl(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }

    @Override
    public void createCuisine(Cuisine cuisine) {
        cuisineRepository.save(cuisine);
    }

    @Override
    public List<Cuisine> getAll(){
        return cuisineRepository.findAll();
    }

    @Override
    public Optional<Cuisine> findCuisineById(Long id){
        return cuisineRepository.findById(id);
    }

    @Override
    public Optional<Cuisine> findCuisineByName(String name) {
        return cuisineRepository.getCuisineByName(name);
    }

}
