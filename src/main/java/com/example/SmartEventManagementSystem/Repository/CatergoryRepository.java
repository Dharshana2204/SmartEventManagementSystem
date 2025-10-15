package com.example.SmartEventManagementSystem.Repository;

import com.example.SmartEventManagementSystem.Entities.Category;
import com.example.SmartEventManagementSystem.Entities.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatergoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName( String name);
}
