package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishContent;
import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.entity.response.DishResponse;
import com.groupproject.nstu.cookbook.entity.response.DishResponseIngredient;
import com.groupproject.nstu.cookbook.entity.DishType;
import com.groupproject.nstu.cookbook.repository.DishRepository;
import com.groupproject.nstu.cookbook.service.interfaces.DishService;
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
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final IngredientServiceImpl ingredientService;
    private final DishContentServiceImpl dishContentService;
    private final DishTypeServiceImpl dishTypeService;
    private final CuisineServiceImpl cuisineService;

    public DishServiceImpl(DishRepository dishRepository, DishTypeServiceImpl dishTypeService, CuisineServiceImpl cuisineService, IngredientServiceImpl ingredientService,/*, DishContentServiceImpl dishContentService*/DishContentServiceImpl dishContentService) {
        this.dishRepository = dishRepository;
        this.ingredientService = ingredientService;
        this.dishTypeService = dishTypeService;
        this.cuisineService = cuisineService;
        this.dishContentService = dishContentService;
    }

    @Override
    public Optional<Dish> findDishById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public ResponseEntity createDish(Dish dish) {

        try {
            if (findDishByName(dish.getName()).isEmpty()) {
                dishRepository.save(dish);
            } else
                throw new SQLException("This dish already exist");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @Override
    public List<DishResponse> getAll() {

        List<DishResponse> dishResponseList = new ArrayList<>();
        List<Dish> dishList = dishRepository.findAll();


        for(Dish dish: dishList){

            Optional<DishResponse> dishResponse = findDishResponseByName(dish.getName());

            if(dishResponse.isPresent()){
                dishResponseList.add(dishResponse.get());
            }

        }

        return dishResponseList;

    }


    @Override
    public Optional<Dish> findDishByName(String name) {

        return dishRepository.getDishByName(name);

    }


    @Override
    public Optional<DishResponse> findDishResponseByName(String name) {

        DishResponse dishResponse = new DishResponse();
        List<Dish> dishList = dishRepository.findAll();

        Dish dish = null;
        for(Dish dish1: dishList){
            if(dish1.getName().equals(name)){
                dish=dish1;
                break;
            }
        }

        List<DishContent> dishContentList = dishContentService.findDishContentByDish(dish);

        Dish findDish = dishContentList.get(0).getDish();
        dishResponse.setName(findDish.getName());
        dishResponse.setCookingDescription(findDish.getCookingDescription());
        dishResponse.setDishType(findDish.getDishType().getName());
        dishResponse.setDishCuisine(findDish.getDishCuisine().getName());

        for (DishContent dishContent: dishContentList){

            DishResponseIngredient dishResponseIngredient = new DishResponseIngredient();

            dishResponseIngredient.setIngredientName(dishContent.getIngredient().getName());
            dishResponseIngredient.setAmountOfIngredient(dishContent.getAmountOfIngredient());
            dishResponseIngredient.setMeasure(dishContent.getIngredient().getMeasure());

            dishResponse.getDishContentList().add(dishResponseIngredient);

        }

        dishResponse.setPictureURL(dishContentList.get(0).getDish().getDishPicture());

        return Optional.of(dishResponse);

    }

    @Override
    public List<Dish> findDishByNames(String names) {
        Specification<Dish> specification = (root, criteriaQuery, criteriaBuilder) -> {
            String[] splitNames = names.split(" ");
            List<Predicate> predicates = new ArrayList<Predicate>();

            for (String splitName : splitNames) {
                predicates.add(criteriaBuilder.equal(root.<String>get("name"), splitName));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        };

        return dishRepository.findAll(specification);
    }

    @Override
    public List<Dish> findDishByCuisinesAndDishType(String cuisines, String dishType) {
        if (!cuisines.equals("-") || !dishType.equals("-")) {
            Specification<Dish> specification = (root, criteriaQuery, criteriaBuilder) -> {
                if (!cuisines.equals("-") && !dishType.equals("-")) {
                    String[] splitCuisines = cuisines.split(" ");
                    List<Predicate> orPredicates = new ArrayList<Predicate>();

                    for (String splitName : splitCuisines) {
                        orPredicates.add(criteriaBuilder.equal(root.<Cuisine>get("dishCuisine").<String>get("name"), splitName));
                    }
                    Predicate orPredicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    predicates.add(orPredicate);
                    predicates.add(criteriaBuilder.equal(root.<DishType>get("dishType").<String>get("name"), dishType));

                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
                else if (!cuisines.equals("-") && dishType.equals("-")) {
                    String[] splitCuisines = cuisines.split(" ");
                    List<Predicate> orPredicates = new ArrayList<Predicate>();

                    for (String splitName : splitCuisines) {
                        orPredicates.add(criteriaBuilder.equal(root.<Cuisine>get("dishCuisine").<String>get("name"), splitName));
                    }

                    return criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
                }
                else if (cuisines.equals("-") && !dishType.equals("-")) {
                    return criteriaBuilder.equal(root.<DishType>get("dishType").<String>get("name"), dishType);
                }

                return null;
            };
            return dishRepository.findAll(specification);
        }

        return dishRepository.findAll();
    }

    @Override
    public List<DishResponse> findDishByAllFilters(String ingredients, String dishType, String cuisines) {
        List<Ingredient> ingredientList = null;
        boolean isDishEligible = true;

        if (!ingredients.equals("-"))
            ingredientList = ingredientService.findIngredientByNames(ingredients);

        List<Dish> dishList = this.findDishByCuisinesAndDishType(cuisines, dishType);

        List<DishResponse> resultList = new ArrayList();

        for (Dish dish : dishList) {

            if (ingredientList != null) {
                int amountOfMatchedIngredients = 0;

                for (DishContent dishContent : dish.getDishContentList()) {

                    for (Ingredient ingredient : ingredientList) {

                        if (ingredient.getId() == dishContent.getIngredient().getId())
                            amountOfMatchedIngredients++;

                    }

                }
                isDishEligible = amountOfMatchedIngredients == ingredientList.size() && ingredientList.size() != 0;
            }

                if (isDishEligible) {
                    List<DishContent> dishContentList = dish.getDishContentList();
                    if (!dishContentList.isEmpty()) {
                        DishResponse dishResponse = new DishResponse();
                        DishContent tempDishContent = dishContentList.get(0);

                        dishResponse.setName(tempDishContent.getDish().getName());
                        dishResponse.setCookingDescription(tempDishContent.getDish().getCookingDescription());
                        dishResponse.setDishType(tempDishContent.getDish().getDishType().getName());
                        dishResponse.setDishCuisine(tempDishContent.getDish().getDishCuisine().getName());
                        dishResponse.setPictureURL(tempDishContent.getDish().getDishPicture());

                        for (DishContent dishContent : dishContentList) {
                            DishResponseIngredient dishResponseIngredient = new DishResponseIngredient();

                            dishResponseIngredient.setIngredientName(dishContent.getIngredient().getName());
                            dishResponseIngredient.setAmountOfIngredient(dishContent.getAmountOfIngredient());
                            dishResponseIngredient.setMeasure(dishContent.getIngredient().getMeasure());

                            dishResponse.getDishContentList().add(dishResponseIngredient);

                        }

                        resultList.add(dishResponse);
                    }
                }

        }

        return resultList;

    }

    @Override
    public ResponseEntity updateDish(Long id, Dish newDish) {
        try {
            Optional<Dish> dishForUpdate = findDishById(id);
            if (dishForUpdate.isPresent()) {
                // Обновление названия блюда
                if (!dishForUpdate.get().getName().equals(newDish.getName())) {
                    Optional<Dish> dishForConstraintCheck = findDishByName(newDish.getName());
                    if (dishForConstraintCheck.isPresent())
                        throw new SQLException("This dish already exist");
                    else
                        dishForUpdate.get().setName(newDish.getName());
                }

                // Обновление описания приготовления
                dishForUpdate.get().setCookingDescription(newDish.getCookingDescription());

                // Обновление типа блюда
                Optional<DishType> dishTypeForCheck = dishTypeService.findDishTypeById(newDish.getDishType().getId());
                if (dishTypeForCheck.isPresent())
                    dishForUpdate.get().setDishType(dishTypeForCheck.get());
                else
                    throw new Exception("Didn't find such dish type");

                // Обновление кухни блюда
                Optional<Cuisine> cuisineForCheck = cuisineService.findCuisineById(newDish.getDishCuisine().getId());
                if (cuisineForCheck.isPresent())
                    dishForUpdate.get().setDishCuisine(cuisineForCheck.get());
                else
                    throw new Exception("Didn't find such cuisine");
            } else
                throw new Exception("Didn't find such dish");

            dishRepository.save(dishForUpdate.get());
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
    public ResponseEntity deleteDish(Long id) {
        try {
            dishRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
