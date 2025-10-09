package com.example.SmartEventManagementSystem.Repository;

import com.example.SmartEventManagementSystem.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatergoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
