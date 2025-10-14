package com.example.SmartEventManagementSystem.Config;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Use a fixed name (e.g., "REGISTRATION_SMTP") to easily retrieve the config
    @Column(unique = true, nullable = false)
    private String configName;

    @Column(nullable = false)
    private String host;
    private int port;

    @Column(nullable = false)
    private String username;

    // This should be encrypted/decrypted in a real application.
    private String password;

    private boolean smtpAuth = true;
    private boolean startTlsEnable = true;

}
