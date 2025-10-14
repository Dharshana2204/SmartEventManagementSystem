package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.Config.EmailConfig;
import com.example.SmartEventManagementSystem.Entities.eventMode;
import com.example.SmartEventManagementSystem.Repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;
    private static final String CONFIG_NAME = "REGISTRATION_SMTP";
    /**
     * Creates a JavaMailSender instance dynamically using the database configuration.
     */
    private JavaMailSender getMailSender(EmailConfig config) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // 1. Set core connection details from the DB entity
        mailSender.setHost(config.getHost());
        mailSender.setPort(config.getPort());
        mailSender.setUsername(config.getUsername());
        mailSender.setPassword(config.getPassword());

        // 2. Set SMTP properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", String.valueOf(config.isSmtpAuth()));
        props.put("mail.smtp.starttls.enable", String.valueOf(config.isStartTlsEnable()));

        return mailSender;
    }
    /**
     * Fetches configuration from DB, sends the actual email, and handles failures.
     * Renamed method to match usage in RegistrationService.
     */
    public void sendRegistrationMail(String to, String eventName, String venueName, String city) {

        // 1. Load configuration from Database
        EmailConfig config = emailRepository.findByConfigName(CONFIG_NAME)
                .orElseThrow(() -> new RuntimeException("Email configuration not found in DB for: " + CONFIG_NAME));

        // 2. Build Dynamic Mail Sender
        JavaMailSender mailSender = getMailSender(config);

        // 3. Construct the Email Message
        SimpleMailMessage msg = new SimpleMailMessage();

        // Set the 'From' address using DB config's username
        msg.setFrom(config.getUsername());
        msg.setTo(to);
        msg.setSubject("Your Registration for " + eventName + " is Confirmed!");

        msg.setText(
                "Hello,\n\n" +
                        "Thank you for registering for the event: " + eventName + ".\n" +
                        "Details:\n" +
                        "  Event: " + eventName + "\n" +
                        "  Venue: " + venueName + "\n" +
                        "  City: " + city + "\n\n" +
                        "We look forward to seeing you there!\n\n" +
                        "Best,\nSmart Event Management Team"
        );

        // 4. Send the Mail
        try
        {
            mailSender.send(msg);
            System.out.println("REAL EMAIL SENT (Dynamic Config): To: " + to);
        }
        catch (Exception e)
        {
            System.err.println("EMAIL FAILED: Dynamic SMTP error. Error: " + e.getMessage());
        }
    }

}
