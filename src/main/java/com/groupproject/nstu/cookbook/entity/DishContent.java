package com.groupproject.nstu.cookbook.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dish_content")
public class DishContent implements Serializable {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount_of_ingredient")
    private float amountOfIngredient;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "DishContent{" +
                "id=" + id +
                ", ingredient=" + ingredient +
                '}';
    }
}