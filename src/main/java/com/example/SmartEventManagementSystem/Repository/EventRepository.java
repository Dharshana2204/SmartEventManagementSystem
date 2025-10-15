package com.example.SmartEventManagementSystem.Repository;

import com.example.SmartEventManagementSystem.Entities.Event;
import com.example.SmartEventManagementSystem.Entities.eventMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>
{
    List<Event> findByCategoryNameIgnoreCase(String name);
    List<Event> findByMode(eventMode mode);

}
