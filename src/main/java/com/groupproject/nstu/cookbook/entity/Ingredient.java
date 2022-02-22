package com.groupproject.nstu.cookbook.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table (name = "ingredient")
public class Ingredient {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    public Ingredient() {}

    public Ingredient(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    @Column(name = "measure")
    private String measure;

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
}
