package com.groupproject.nstu.cookbook.repository;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.DishType;
import com.groupproject.nstu.cookbook.entity.response.DishResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long>, JpaSpecificationExecutor<Dish> {

    @Query("select d from Dish d where lower(d.name) = lower(:name)")
    Optional<DishResponse> getDishResponseByName(@Param("name") String name);

    @Query("select d from Dish d where lower(d.name) = lower(:name)")
    Optional<Dish> getDishByName(@Param("name") String name);


}
