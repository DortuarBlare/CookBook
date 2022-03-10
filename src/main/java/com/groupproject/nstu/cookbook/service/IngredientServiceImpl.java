package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.repository.IngredientRepository;
import com.groupproject.nstu.cookbook.service.interfaces.IngredientService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
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

    @Override
    public ResponseEntity updateIngredient(Long id, Ingredient newIngredient) {
        try {
            Optional<Ingredient> ingredient = findIngredientById(id);
            if (ingredient.isPresent())
            {
                Optional<Ingredient> ingredientForConstraintCheck = findIngredientByName(newIngredient.getName());
                if (ingredientForConstraintCheck.isPresent())
                    throw new SQLException("This ingredient already exist");
                else
                    ingredient.get().setName(newIngredient.getName());
            }
            else
                throw new Exception("Didn't find such ingredient");

            ingredientRepository.save(ingredient.get());
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
    public ResponseEntity deleteIngredient(Long id) {
        try {
            ingredientRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
