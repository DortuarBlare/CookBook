package com.groupproject.nstu.cookbook.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "ingredient")
public class Ingredient implements Serializable {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "measure")
    private String measure;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishContent> dishContentList = new ArrayList<>();

    public Ingredient() {}

    public Ingredient(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public void addDishContent(DishContent dishContent) {
        dishContent.setIngredient(this);
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

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public List<DishContent> getDishContentList() {
        return dishContentList;
    }

    public void setDishContentList(List<DishContent> dishContentList) {
        this.dishContentList = dishContentList;
    }
}
