package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.repository.IngredientRepository;
import com.groupproject.nstu.cookbook.service.interfaces.IngredientService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Optional<Ingredient> findIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public void createIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> findIngredientByName(String name) {
        return ingredientRepository.getIngredientByName(name);
    }

    @Override
    public List<Ingredient> findIngredientByNames(String names) {
        if (names != "") {
            Specification<Ingredient> specification = (root, criteriaQuery, criteriaBuilder) -> {
                String[] splitNames = names.split(" ");
                List<Predicate> predicates = new ArrayList<Predicate>();

                for (String splitName : splitNames) {
                    predicates.add(criteriaBuilder.equal(root.<String>get("name"), splitName));
                }

                return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
            };
            return ingredientRepository.findAll(specification);
        }
        return null;
    }
}
