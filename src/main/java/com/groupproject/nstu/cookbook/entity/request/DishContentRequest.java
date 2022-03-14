package com.groupproject.nstu.cookbook.entity.request;

public class DishContentRequest {
    private float amountOfIngredient;
    private String dish;
    private String ingredient;

    public float getAmountOfIngredient() {
        return amountOfIngredient;
    }

    public void setAmountOfIngredient(float amountOfIngredient) {
        this.amountOfIngredient = amountOfIngredient;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
