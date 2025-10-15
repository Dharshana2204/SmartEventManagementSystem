package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.Config.EmailConfig;
import com.example.SmartEventManagementSystem.Entities.eventMode;
//import com.example.SmartEventManagementSystem.Repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    public String sendRegistrationMail(String Email,String eventName, String venue, String city) {
        String subject = "Registration Confirmed: " + eventName;
        String body = "Hello,\n\n" +
                "You have successfully registered for the event: " + eventName + "\n" +
                "Venue: " + venue + "\n" +
                "City: " + city + "\n\n" +
                "Best regards,\nSmart Event Management Team";
//        System.out.println("Sending email...");
//        System.out.println("To: " + Email );
//        System.out.println("SUBJECT: " + subject);
//        System.out.println("BODY:\n" + body);
        return body;
    }
}
