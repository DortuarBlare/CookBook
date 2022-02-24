package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.repository.CuisineRepository;
import com.groupproject.nstu.cookbook.service.interfaces.CuisineService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

}
