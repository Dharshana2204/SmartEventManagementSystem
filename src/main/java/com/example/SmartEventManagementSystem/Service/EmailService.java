package com.example.SmartEventManagementSystem.Service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        // Simple mock: in real app integrate an SMTP client or external provider
        System.out.println("[EmailService] Sending email to: " + to + " subject: " + subject);
    }
}
