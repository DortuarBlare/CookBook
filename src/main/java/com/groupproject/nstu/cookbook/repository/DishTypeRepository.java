package com.groupproject.nstu.cookbook.repository;

import com.groupproject.nstu.cookbook.entity.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishTypeRepository extends JpaRepository<DishType, Long> {
}
