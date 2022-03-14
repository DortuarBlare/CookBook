package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.request.CuisineRequest;
import com.groupproject.nstu.cookbook.repository.CuisineRepository;
import com.groupproject.nstu.cookbook.service.interfaces.CuisineService;
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
public class CuisineServiceImpl implements CuisineService {

    private final CuisineRepository cuisineRepository;

    public CuisineServiceImpl(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }

    @Override
    public void createCuisine(CuisineRequest cuisineRequest) {
        Cuisine cuisine = new Cuisine();
        cuisine.setName(cuisineRequest.getName());
        cuisineRepository.save(cuisine);
    }

    @Override
    public List<Cuisine> getAll() {
        return cuisineRepository.findAll();
    }

    @Override
    public Optional<Cuisine> findCuisineById(Long id) {
        return cuisineRepository.findById(id);
    }

    @Override
    public Optional<Cuisine> findCuisineByName(String name) {
        return cuisineRepository.getCuisineByName(name);
    }

    @Override
    public List<Cuisine> findCuisineByNames(String names) {
        Specification<Cuisine> specification = (root, criteriaQuery, criteriaBuilder) -> {
            String[] splitNames = names.split(" ");
            List<Predicate> predicates = new ArrayList<Predicate>();

            for (String splitName : splitNames) {
                predicates.add(criteriaBuilder.equal(root.<String>get("name"), splitName));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        };

        return cuisineRepository.findAll(specification);
    }

    @Override
    public ResponseEntity updateCuisine(Long id, CuisineRequest cuisineRequest) {
        try {
            Optional<Cuisine> cuisine = findCuisineById(id);
            if (cuisine.isPresent())
            {
                Optional<Cuisine> cuisineForConstraintCheck = findCuisineByName(cuisineRequest.getName());
                if (cuisineForConstraintCheck.isPresent())
                    throw new SQLException("This cuisine already exist");
                else
                    cuisine.get().setName(cuisineRequest.getName());
            }
            else
                throw new Exception("Didn't find such cuisine");

            cuisineRepository.save(cuisine.get());
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
    public ResponseEntity deleteCuisine(Long id) {
        try {
            cuisineRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
