package com.groupproject.nstu.cookbook.entity.request;

import com.groupproject.nstu.cookbook.entity.DishType;

public class DishRequest {
    private String name;
    private String dishPicture;
    private String cookingDescription;
    private String dishType;
    private String cuisine;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDishPicture() {
        return dishPicture;
    }

    public void setDishPicture(String dishPicture) {
        this.dishPicture = dishPicture;
    }

    public String getCookingDescription() {
        return cookingDescription;
    }

    public void setCookingDescription(String cookingDescription) {
        this.cookingDescription = cookingDescription;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
