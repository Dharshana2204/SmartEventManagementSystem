package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.Entities.Event;
import com.example.SmartEventManagementSystem.Entities.Registration;
import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Exception.ResourceNotFoundException;
import com.example.SmartEventManagementSystem.Repository.EventRepository;
import com.example.SmartEventManagementSystem.Repository.RegistrationRepository;
import com.example.SmartEventManagementSystem.Repository.UserRepository;
import com.example.SmartEventManagementSystem.Service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EmailService emailService;

    public RegistrationController(RegistrationRepository registrationRepository, UserRepository userRepository, EventRepository eventRepository, EmailService emailService) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<List<Registration>> all() {
        return ResponseEntity.ok(registrationRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Registration> register(@RequestParam Long userId, @RequestParam Integer eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        long current = registrationRepository.countByEvent(event);
        if (event.getCapacity() > 0 && current >= event.getCapacity()) {
            throw new IllegalArgumentException("Event is full");
        }

        Registration reg = new Registration();
        reg.setUser(user);
        reg.setEvent(event);
        reg.setStatus("CONFIRMED");
        Registration saved = registrationRepository.save(reg);

        // send confirmation (mock)
        emailService.sendEmail(user.getEmail(), "Registration Confirmed", "You are registered for event: " + event.getEventTitle());

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        Registration reg = registrationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registration not found: " + id));
        registrationRepository.delete(reg);
        return ResponseEntity.noContent().build();
    }
}
