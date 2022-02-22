package com.groupproject.nstu.cookbook.repository;

import com.groupproject.nstu.cookbook.entity.DishContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishContentRepository extends JpaRepository<DishContent, Long> {
}
