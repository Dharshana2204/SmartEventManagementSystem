package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.DTO.RegistrationCountDTO;
import com.example.SmartEventManagementSystem.DTO.RegistrationDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> registerUser(Long userId,Long eventId)
    {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","ID",userId));
        Event event=eventRepository.findById(eventId).orElseThrow(()->new ResourceNotFoundException("Event","ID",eventId));
        Optional<Registration> existingRegistration=registrationRepository.findByUserIdAndEventId(userId, eventId);
        if(existingRegistration.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User email "+user.getEmail()+" already exists");
        }
        Registration registration=new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        registrationRepository.save(registration);
        String venueName;
        String venueCity;
        //Condition for Email logic
        if(event.getMode()==eventMode.ONLINE)
        {
            //Online mode
            venueName = "Virtual Event (Link will be sent closer to the Date)";
            venueCity = "N/A";
        }
        else
        {
            //Physical Location for Hybrid
            venueName = event.getVenueName();
            venueCity = event.getVenueCity();
        }
        String emailBody=emailService.sendRegistrationMail(user.getEmail(),
                                          event.getEventTitle(),
                                          venueName,
                                          venueCity);
        RegistrationDTO response= new RegistrationDTO(registration.getRegistrationId(),user.getId(),user.getName(),user.getEmail(),event.getId(),event.getEventTitle(),venueName,venueCity,emailBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //Get All reg Details
    public List<RegistrationDTO> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();

        return registrations.stream()
                             .map(reg -> {
            Event event = reg.getEvent();
            User user = reg.getUser();

            String venueName = (event.getMode() == eventMode.ONLINE) ?
                    "Virtual Event (Link will be sent closer to the Date)" : event.getVenueName();
            String venueCity = (event.getMode() == eventMode.ONLINE) ? "N/A" : event.getVenueCity();

            return new RegistrationDTO(
                    reg.getRegistrationId(),
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    event.getId(),
                    event.getEventTitle(),
                    venueName,
                    venueCity,
                    null
            );
        }).collect(Collectors.toList());
    }
    //Get reg details by mode
    public List<RegistrationDTO> getRegistrationsByEventMode(eventMode mode)
    {
        List<Registration> registrations = registrationRepository.findByEventMode(mode);

        return registrations.stream().map(reg -> {
            Event event = reg.getEvent();
            User user = reg.getUser();
            String venueName = (event.getMode() == eventMode.ONLINE) ?
                    "Virtual Event (Link will be sent closer to the Date)" : event.getVenueName();
            String venueCity = (event.getMode() == eventMode.ONLINE) ? "N/A" : event.getVenueCity();

            return new RegistrationDTO(
                    reg.getRegistrationId(),
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    event.getId(),
                    event.getEventTitle(),
                    venueName,
                    venueCity,
                    null
            );
        }).collect(Collectors.toList());
    }
    //Get reg count on mode basis
    public RegistrationCountDTO getRegistrationCountByEventAndMode(Long eventId, eventMode mode) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "ID", eventId));

        long count = registrationRepository.countByEventIdAndEventMode(eventId, mode);

        return new RegistrationCountDTO(
                event.getId(),
                event.getEventTitle(),
                mode,
                count
        );
    }


}
