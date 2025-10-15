package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.DTO.RegistrationCountDTO;
import com.example.SmartEventManagementSystem.DTO.RegistrationDTO;
import com.example.SmartEventManagementSystem.Entities.Registration;
import com.example.SmartEventManagementSystem.Entities.eventMode;
import com.example.SmartEventManagementSystem.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @PostMapping("/register/{eventId}")
    public ResponseEntity<?> registerUserForEvent(@PathVariable Long eventId, @RequestParam Long userId)
    {

        return registrationService.registerUser(userId,eventId);
    }
    @GetMapping("/all")
    public ResponseEntity<List<RegistrationDTO>> getAllRegistrations() {
        List<RegistrationDTO> registrations = registrationService.getAllRegistrations();

        if (registrations.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/mode")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByMode(@RequestParam eventMode mode) {
        List<RegistrationDTO> registrations = registrationService.getRegistrationsByEventMode(mode);

        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(registrations);
        }
    }

    @GetMapping("/count/{eventId}")
    public ResponseEntity<RegistrationCountDTO> getRegistrationCountByMode(
            @PathVariable Long eventId,
            @RequestParam eventMode mode) {

        RegistrationCountDTO countDTO = registrationService.getRegistrationCountByEventAndMode(eventId, mode);
        return ResponseEntity.ok(countDTO);
    }

}
