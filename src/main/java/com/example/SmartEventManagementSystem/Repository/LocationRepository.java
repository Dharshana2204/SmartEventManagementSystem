package com.example.SmartEventManagementSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// No Location entity file in repo; create a marker interface for future use.
@Repository
public interface LocationRepository extends JpaRepository<Object, Long> {
}