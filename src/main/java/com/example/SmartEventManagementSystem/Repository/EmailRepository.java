package com.example.SmartEventManagementSystem.Repository;

import com.example.SmartEventManagementSystem.Config.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailConfig,Long> {
    Optional<EmailConfig> findByConfigName(String configName);
}
