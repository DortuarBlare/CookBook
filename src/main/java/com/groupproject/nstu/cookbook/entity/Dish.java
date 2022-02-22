package com.groupproject.nstu.cookbook.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dish")
public class Dish implements Serializable {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cooking_description")
    private String cookingDescription;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishContent> dishContentList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "type_id")
    private DishType dishType;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cuisine_id")
    private Cuisine dishCuisine;

    public Dish() {}

    public void addDishContent(DishContent dishContent) {
        dishContent.setDish(this);
        dishContentList.add(dishContent);
    }

    public void removeDishContent(DishContent dishContent) {
        dishContentList.remove(dishContent);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public Cuisine getDishCuisine() {
        return dishCuisine;
    }

    public void setDishCuisine(Cuisine dishCuisine) {
        this.dishCuisine = dishCuisine;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cookingDescription='" + cookingDescription + '\'' +
                '}';
    }
}
