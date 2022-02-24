package com.groupproject.nstu.cookbook.repository;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

    @Query("select c from Cuisine c where c.name = :name")
    Optional<Cuisine> getCuisineByName(@Param("name") String name);

}
