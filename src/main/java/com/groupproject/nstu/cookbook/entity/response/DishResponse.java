package com.groupproject.nstu.cookbook.entity.response;

import java.util.ArrayList;
import java.util.List;

public class DishResponse {

    private Long dishID;
    private String name;
    private String cookingDescription;
    private String dishType;
    private String dishCuisine;
    private String pictureURL;
    private List<DishResponseIngredient> dishContentList = new ArrayList<>();

    public Long getDishID() {
        return dishID;
    }

    public void setDishID(Long dishID) {
        this.dishID = dishID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCookingDescription() {
        return cookingDescription;
    }

    public void setCookingDescription(String cookingDescription) {
        this.cookingDescription = cookingDescription;
    }

    public List<DishResponseIngredient> getDishContentList() {
        return dishContentList;
    }

    public void setDishContentList(List<DishResponseIngredient> dishContentList) {
        this.dishContentList = dishContentList;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String getDishCuisine() {
        return dishCuisine;
    }

    public void setDishCuisine(String dishCuisine) {
        this.dishCuisine = dishCuisine;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

}
