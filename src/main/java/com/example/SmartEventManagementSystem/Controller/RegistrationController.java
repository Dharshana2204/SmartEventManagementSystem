package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.Entities.Registration;
import com.example.SmartEventManagementSystem.Entities.eventMode;
import com.example.SmartEventManagementSystem.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @PostMapping("/{eventId}")
    public ResponseEntity<Registration> registerUserForEvent(@PathVariable Long eventId, @RequestParam Long userId)
    {

        Registration newRegistration = registrationService.registerUser(userId,eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistration);
    }
    @GetMapping("/mode")
    public ResponseEntity<List<Registration>> getRegistrationsByMode(@RequestParam eventMode mode) {
        List<Registration> registrations = registrationService.getRegistrationsByEventMode(mode);

        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(registrations);
        }
    }
}
