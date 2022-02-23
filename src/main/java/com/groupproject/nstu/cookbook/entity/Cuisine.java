package com.groupproject.nstu.cookbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuisine_table")
public class Cuisine implements Serializable {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "dishCuisine", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
    @JsonIgnore
    private List<Dish> cuisineDishList = new ArrayList<>();

    public Cuisine() {}

    public Cuisine(String name) {
        this.name = name;
    }

    public Cuisine(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addDish(Dish dish) {
        dish.setDishCuisine(this);
        cuisineDishList.add(dish);
    }

    public void removeDish(Dish dish) {
        cuisineDishList.remove(dish);
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

    public List<Dish> getCuisineDishList() {
        return cuisineDishList;
    }

    public void setCuisineDishList(List<Dish> cuisineDishList) {
        this.cuisineDishList = cuisineDishList;
    }

    @Override
    public String toString() {
        return "Cuisine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
