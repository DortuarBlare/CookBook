package com.groupproject.nstu.cookbook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.groupproject.nstu.cookbook.entity.converter.CustomConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dish_content")
public class DishContent implements Serializable {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount_of_ingredient")
    private float amountOfIngredient;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dish_id")
//    @JsonBackReference
    private Dish dish;

    @Convert(converter = CustomConverter.class)
    private List<Ingredient> ingredients = new ArrayList<>();

//    public void addIngredient(Ingredient ingredient) {
//        ingredients.add(ingredient);
//        ingredient.setDishContent(this);
//    }
//    public void removeIngredient(Ingredient ingredient) {
//        ingredients.remove(ingredient);
//        ingredient.setDishContent(null);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getAmountOfIngredient() {
        return amountOfIngredient;
    }

    public void setAmountOfIngredient(float amountOfIngredient) {
        this.amountOfIngredient = amountOfIngredient;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public List<Ingredient> getIngredient() {
        return ingredients;
    }

    public void setIngredient(List<Ingredient> ingredient) {
        this.ingredients = ingredient;
    }
//    public Ingredient getIngredient() {
//        return ingredient;
//    }
//
//    public void setIngredient(Ingredient ingredient) {
//        this.ingredient = ingredient;
//    }

    @Override
    public String toString() {
        return "DishContent{" +
                "id=" + id +
                ", ingredient=" + ingredients +
                '}';
    }
}