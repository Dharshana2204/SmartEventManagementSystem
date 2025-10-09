package com.example.SmartEventManagementSystem.Repository;

import com.example.SmartEventManagementSystem.Entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}