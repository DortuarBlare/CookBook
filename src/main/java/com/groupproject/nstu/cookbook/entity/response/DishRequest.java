package com.groupproject.nstu.cookbook.entity.response;

import com.groupproject.nstu.cookbook.entity.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishRequest {

    private Dish dish;
    private List<DishRequestIngredient> dishContentList = new ArrayList<>();

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public List<DishRequestIngredient> getDishContentList() {
        return dishContentList;
    }

    public void setDishContentList(List<DishRequestIngredient> dishContentList) {
        this.dishContentList = dishContentList;
    }
}
