package com.groupproject.nstu.cookbook.repository;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishContentRepository extends JpaRepository<DishContent, Long>, JpaSpecificationExecutor<DishContent> {

    List<DishContent> findDishContentByDish(Dish dish);

}
