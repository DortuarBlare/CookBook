package com.groupproject.nstu.cookbook.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Cook_book_table")
public class CookBook {

    @Id
    @GenericGenerator(name = "genegator", strategy = "generator")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
