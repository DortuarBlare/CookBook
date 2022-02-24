package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.DishType;
import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.repository.DishTypeRepository;
import com.groupproject.nstu.cookbook.service.interfaces.DishTypeService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DishTypeServiceImpl implements DishTypeService {

    private final DishTypeRepository dishTypeRepository;

    public DishTypeServiceImpl(DishTypeRepository dishTypeRepository) {
        this.dishTypeRepository = dishTypeRepository;
    }

    @Override
    public Optional<DishType> findDishTypeById(Long id) {
        return dishTypeRepository.findById(id);
    }

    @Override
    public void createDishType(DishType dishType) {
        dishTypeRepository.save(dishType);
    }

    @Override
    public List<DishType> getAll() {
        return dishTypeRepository.findAll();
    }

    @Override
    public Optional<DishType> findDishTypeByName(String name) {
        return dishTypeRepository.getDishTypeByName(name);
    }

    @Override
    public List<DishType> findDishTypeByNames(String names) {
        Specification<DishType> specification = (root, criteriaQuery, criteriaBuilder) -> {
            String[] splitNames = names.split(" ");
            List<Predicate> predicates = new ArrayList<Predicate>();

            for (String splitName : splitNames) {
                predicates.add(criteriaBuilder.equal(root.<String>get("name"), splitName));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        };

        return dishTypeRepository.findAll(specification);
    }


}
