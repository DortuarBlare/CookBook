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

    public DishServiceImpl(DishRepository dishRepository, DishTypeServiceImpl dishTypeService, CuisineServiceImpl cuisineService, IngredientServiceImpl ingredientService, DishContentServiceImpl dishContentService) {
        this.dishRepository = dishRepository;
        this.ingredientService = ingredientService;
        this.dishContentService = dishContentService;
        this.dishTypeService = dishTypeService;
        this.cuisineService = cuisineService;
    }

    @Override
    public Optional<Dish> findDishById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public void createDish(Dish dish) {
        dishRepository.save(dish);
    }

    @Override
    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    @Override
    public Optional<Dish> findDishByName(String name) {
        return dishRepository.getDishByName(name);
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
//        Specification<Dish> specification = (root, criteriaQuery, criteriaBuilder) -> {
//            String[] splitCuisines = cuisines.split(" ");
//            List<Predicate> predicates = new ArrayList<Predicate>();
//
//            List<Predicate> andPredicates = new ArrayList<Predicate>();
//            Predicate andPredicate;
//            for (String splitName : splitCuisines) {
//
//                andPredicates.add(criteriaBuilder.equal(root.<Cuisine>get("dishCuisine").<String>get("name"), splitName));
//                andPredicates.add(criteriaBuilder.equal(root.<DishType>get("dishType").<String>get("name"), dishType));
//                andPredicate = criteriaBuilder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
//                predicates.add(andPredicate);
//
//                andPredicates.clear();
//                andPredicate = null;
//            }
//
//            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
//        };
        if (cuisines != null || !cuisines.equals("") || dishType != null || !dishType.equals("")) {
            Specification<Dish> specification = (root, criteriaQuery, criteriaBuilder) -> {
                if (!cuisines.equals("") && dishType != null && !dishType.equals("")) {
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
                else if (!cuisines.equals("") && (dishType == null || dishType.equals(""))) {
                    String[] splitCuisines = cuisines.split(" ");
                    List<Predicate> orPredicates = new ArrayList<Predicate>();

                    for (String splitName : splitCuisines) {
                        orPredicates.add(criteriaBuilder.equal(root.<Cuisine>get("dishCuisine").<String>get("name"), splitName));
                    }

                    return criteriaBuilder.and(orPredicates.toArray(new Predicate[orPredicates.size()]));
                }
                else if (cuisines.equals("") && (dishType != null || !dishType.equals(""))) {
                    return criteriaBuilder.equal(root.<DishType>get("dishType").<String>get("name"), dishType);
                }

                return null;
            };
            return dishRepository.findAll(specification);
        }

        return dishRepository.findAll();
    }

    @Override
    public List<DishResponse> findDish(String ingredients, String dishType, String cuisines) {

        List<Ingredient> ingredientList = null;

        if (ingredients != null || !ingredients.equals("")) {
            ingredientList = ingredientService.findIngredientByNames(ingredients);
        }

        List<Dish> dishList = this.findDishByCuisinesAndDishType(cuisines, dishType);

        List<DishResponse> resultList = new ArrayList();


        for (Dish dish : dishList) {

            if (ingredientList != null) {
                int amountOfMatchedIngredients = 0;

                for (DishContent dishContent : dishContentService.findDishContentByDish(dish)) {

                    for (Ingredient ingredient : ingredientList) {

                        if (ingredient.getId() == dishContent.getIngredient().getId()) {
                            amountOfMatchedIngredients++;
                        }

                    }

                }


                if (amountOfMatchedIngredients == ingredientList.size() && ingredientList.size() != 0) {
                    DishResponse dishResponse = new DishResponse();
                    List<DishContent> dishContentList = dishContentService.findDishContentByDish(dish);
                    dishResponse.setName(dishContentList.get(0).getDish().getName());
                    dishResponse.setCookingDescription(dishContentList.get(0).getDish().getCookingDescription());
                    dishResponse.setDishType(dishContentList.get(0).getDish().getDishType().getName());
                    dishResponse.setDishCuisine(dishContentList.get(0).getDish().getDishCuisine().getName());
                    for (DishContent dishContent : dishContentList) {

                        DishResponseIngredient dishResponseIngredient = new DishResponseIngredient();

                        dishResponseIngredient.setIngredientName(dishContent.getIngredient().getName());
                        dishResponseIngredient.setAmountOfIngredient(dishContent.getAmountOfIngredient());

                        dishResponse.getDishContentList().add(dishResponseIngredient);

                    }

                    dishResponse.setPictureURL(dishContentList.get(0).getDish().getDishPicture());

                    resultList.add(dishResponse);

                }
            }
            else {
                DishResponse dishResponse = new DishResponse();
                List<DishContent> dishContentList = dishContentService.findDishContentByDish(dish);
                dishResponse.setName(dishContentList.get(0).getDish().getName());
                dishResponse.setCookingDescription(dishContentList.get(0).getDish().getCookingDescription());
                dishResponse.setDishType(dishContentList.get(0).getDish().getDishType().getName());
                dishResponse.setDishCuisine(dishContentList.get(0).getDish().getDishCuisine().getName());
                for (DishContent dishContent : dishContentList) {

                    DishResponseIngredient dishResponseIngredient = new DishResponseIngredient();

                    dishResponseIngredient.setIngredientName(dishContent.getIngredient().getName());
                    dishResponseIngredient.setAmountOfIngredient(dishContent.getAmountOfIngredient());

                    dishResponse.getDishContentList().add(dishResponseIngredient);

                }

                dishResponse.setPictureURL(dishContentList.get(0).getDish().getDishPicture());

                resultList.add(dishResponse);
            }

        }

        return resultList;

    }

    @Override
    public ResponseEntity updateDish(Long id, Dish newDish) {
        try {
            Optional<Dish> dish = findDishById(id);
            if (dish.isPresent()) {
                // Обновление названия блюда
                Optional<Dish> dishForConstraintCheck = findDishByName(newDish.getName());
                if (dishForConstraintCheck.isPresent())
                    throw new SQLException("This dish already exist");
                else
                    dish.get().setName(newDish.getName());

                // Обновление описания приготовления
                dish.get().setCookingDescription(newDish.getCookingDescription());

                // Обновление типа блюда
                Optional<DishType> dishTypeForCheck = dishTypeService.findDishTypeById(newDish.getDishType().getId());
                if (dishTypeForCheck.isPresent())
                    dish.get().setDishType(dishTypeForCheck.get());
                else
                    throw new Exception("Didn't find such dish type");

                // Обновление кухни блюда
                Optional<Cuisine> cuisineForCheck = cuisineService.findCuisineById(newDish.getDishCuisine().getId());
                if (cuisineForCheck.isPresent())
                    dish.get().setDishCuisine(cuisineForCheck.get());
                else
                    throw new Exception("Didn't find such cuisine");
            } else
                throw new Exception("Didn't find such ingredient");

            dishRepository.save(dish.get());
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
