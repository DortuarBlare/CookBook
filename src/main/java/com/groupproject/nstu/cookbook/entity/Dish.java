package com.groupproject.nstu.cookbook.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Dish")
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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "type_id")
    private DishType dishType;

    public Dish() {
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

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cookingDescription='" + cookingDescription + '\'' +
                '}';
    }
}
