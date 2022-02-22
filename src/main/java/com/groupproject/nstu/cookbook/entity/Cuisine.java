package com.groupproject.nstu.cookbook.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuisine")
public class Cuisine implements Serializable {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "dishCuisine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> dishList = new ArrayList<>();

    public Cuisine() {}

    public Cuisine(String name) {
        this.name = name;
    }

    public void addDish(Dish dish) {
        dish.setDishCuisine(this);
        dishList.add(dish);
    }

    public void removeDish(Dish dish) {
        dishList.remove(dish);
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

    @Override
    public String toString() {
        return "Cuisine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
