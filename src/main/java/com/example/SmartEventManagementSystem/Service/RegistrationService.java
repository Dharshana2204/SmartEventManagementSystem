package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.Entities.Event;
import com.example.SmartEventManagementSystem.Entities.Registration;
import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Entities.eventMode;
import com.example.SmartEventManagementSystem.Exception.ResourceNotFoundException;
import com.example.SmartEventManagementSystem.Repository.EventRepository;
import com.example.SmartEventManagementSystem.Repository.RegistrationRepository;
import com.example.SmartEventManagementSystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EmailService emailService;
    @Transactional
    public Registration registerUser(Long userId,Long eventId)
    {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","ID",userId));
        Event event=eventRepository.findById(eventId).orElseThrow(()->new ResourceNotFoundException("Event","ID",eventId));
        Optional<Registration> existingRegistration=registrationRepository.findByUserIdAndEventId(userId, eventId);
        if(existingRegistration.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User Email" + user.getEmail() + " already exists");
        }
        Registration registration=new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        //Condition for Email logic
        String venueNameForEmail;
        String venueCityForEmail;
        if(event.getMode()==eventMode.ONLINE)
        {
            //Online mode
            venueNameForEmail = "Virtual Event (Link will be sent closer to the Date)";
            venueCityForEmail = "N/A";
        }
        else
        {
            //Physical Location for Hybrid
            venueNameForEmail = event.getVenueName();
            venueCityForEmail = event.getVenueCity();
        }
        emailService.sendRegistrationMail(user.getEmail(),
                                          event.getEventTitle(),
                                          venueNameForEmail,
                                          venueCityForEmail);
        return registrationRepository.save(registration);

    }
    public List<Registration> getRegistrationsByEventMode(eventMode mode)
    {
        return registrationRepository.findByEventMode(mode);
    }

}
