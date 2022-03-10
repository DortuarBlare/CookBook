package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishContent;
import com.groupproject.nstu.cookbook.entity.Ingredient;
import com.groupproject.nstu.cookbook.entity.response.DishResponse;
import com.groupproject.nstu.cookbook.entity.response.DishResponseIngredient;
import com.groupproject.nstu.cookbook.repository.DishRepository;
import com.groupproject.nstu.cookbook.service.interfaces.DishService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final IngredientServiceImpl ingredientService;
    private final DishContentServiceImpl dishContentService;

    public DishServiceImpl(DishRepository dishRepository, IngredientServiceImpl ingredientService, DishContentServiceImpl dishContentService) {
        this.dishRepository = dishRepository;
        this.ingredientService = ingredientService;
        this.dishContentService = dishContentService;
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
    public List<DishResponse> findDish(String ingredients) {

        List<Ingredient> ingredientList = ingredientService.findIngredientByNames(ingredients);

        List<Dish> dishList = dishRepository.findAll();

        List<DishResponse> resultList = new ArrayList();

        for (Dish dish: dishList){

            int amountOfMatchedIngredients = 0;

            for (DishContent dishContent: dishContentService.findDishContentByDish(dish)){

                for (Ingredient ingredient: ingredientList){

                    if(ingredient.getId() == dishContent.getIngredient().getId()){
                        amountOfMatchedIngredients++;
                    }

                }

            }


            if(amountOfMatchedIngredients==ingredientList.size()){
                DishResponse dishResponse = new DishResponse();
                List<DishContent> dishContentList = dishContentService.findDishContentByDish(dish);
                dishResponse.setName(dishContentList.get(0).getDish().getName());
                dishResponse.setCookingDescription(dishContentList.get(0).getDish().getCookingDescription());
                dishResponse.setDishType(dishContentList.get(0).getDish().getDishType().getName());
                dishResponse.setDishCuisine(dishContentList.get(0).getDish().getDishCuisine().getName());
                for (DishContent dishContent: dishContentList){

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
}
