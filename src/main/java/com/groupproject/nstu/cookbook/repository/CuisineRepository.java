package com.groupproject.nstu.cookbook.repository;

import com.groupproject.nstu.cookbook.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long>, JpaSpecificationExecutor<Cuisine> {

    @Query("select c from Cuisine c where c.name = :name")
    Optional<Cuisine> getCuisineByName(@Param("name") String name);

    @Query("SELECT c FROM Cuisine c WHERE c.name = :queryEnd")
    List<Cuisine> getCuisineByNames(@Param("queryEnd") String queryEnd);

}
