package com.groupproject.nstu.cookbook.entity.response;

import com.groupproject.nstu.cookbook.entity.Ingredient;

public class DishRequestIngredient {

    private Ingredient ingredientName;
    private float amountOfIngredient;

    public Ingredient getIngredient() {
        return ingredientName;
    }

    public void setIngredient(Ingredient ingredientName) {
        this.ingredientName = ingredientName;
    }

    public float getAmountOfIngredient() {
        return amountOfIngredient;
    }

    public void setAmountOfIngredient(float amountOfIngredient) {
        this.amountOfIngredient = amountOfIngredient;
    }


}
