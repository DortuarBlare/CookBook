package com.groupproject.nstu.cookbook.entity.response;

public class DishResponseIngredient {

    private String ingredientName;
    private float amountOfIngredient;
    private String measure;

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public float getAmountOfIngredient() {
        return amountOfIngredient;
    }

    public void setAmountOfIngredient(float amountOfIngredient) {
        this.amountOfIngredient = amountOfIngredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
