package com.example.SmartEventManagementSystem.Repository;

import com.example.SmartEventManagementSystem.Entities.Registration;
import com.example.SmartEventManagementSystem.Entities.eventMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RegistrationRepository extends JpaRepository<Registration,Long> {
    Optional<Registration> findByUserIdAndEventId(Long userId, Long eventId);
    List<Registration> findByEventMode(eventMode mode);
    long countByEventId(Long id);
    long countByEventIdAndEventMode(Long eventID,eventMode mode);

}
