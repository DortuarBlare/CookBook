package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.DishType;
import com.groupproject.nstu.cookbook.entity.request.DishTypeRequest;
import com.groupproject.nstu.cookbook.repository.DishTypeRepository;
import com.groupproject.nstu.cookbook.service.interfaces.DishTypeService;
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
    public void createDishType(DishTypeRequest dishTypeRequest) {
        DishType dishType = new DishType();
        dishType.setName(dishTypeRequest.getName());

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

    @Override
    public ResponseEntity updateDishType(Long id, DishTypeRequest dishTypeRequest) {
        try {
            Optional<DishType> dishType = findDishTypeById(id);
            if (dishType.isPresent())
            {
                Optional<DishType> dishTypeForConstraintCheck = findDishTypeByName(dishTypeRequest.getName());
                if (dishTypeForConstraintCheck.isPresent())
                    throw new SQLException("This dish type already exist");
                else
                    dishType.get().setName(dishTypeRequest.getName());
            }
            else
                throw new Exception("Didn't find such dish type");

            dishTypeRepository.save(dishType.get());
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
    public ResponseEntity deleteDishType(Long id) {
        try {
            dishTypeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
