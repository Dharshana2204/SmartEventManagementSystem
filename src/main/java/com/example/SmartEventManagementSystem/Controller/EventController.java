package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.DTO.EventDTO;
import com.example.SmartEventManagementSystem.Entities.Event;
import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Entities.eventMode;
import com.example.SmartEventManagementSystem.Service.EventService;
import com.example.SmartEventManagementSystem.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService; // to get user by ID or email

    // GET all events - available for all users
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // GET event by ID - available for all users
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    // CREATE event - ADMIN only
    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody EventDTO dto,
            @RequestHeader("userRole") String userRole,
            @RequestHeader("userEmail") String userEmail) {

        User user = userService.getUserByEmail(userEmail); // fetch creator
        Event event = eventService.createEvent(dto, userRole, user);
        return ResponseEntity.status(201).body(event);
    }

    // UPDATE event - ADMIN only
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventDTO dto,
            @RequestHeader("userRole") String userRole,
            @RequestHeader("userEmail") String userEmail) {

        User user = userService.getUserByEmail(userEmail);
        Event updatedEvent = eventService.updateEvent(id, dto, userRole, user);
        return ResponseEntity.ok(updatedEvent);
    }

    // DELETE event - ADMIN only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id,
            @RequestHeader("userRole") String userRole) {

        eventService.deleteEvent(id, userRole);
        return ResponseEntity.noContent().build();
    }

    // GET events by category
    @GetMapping("/category/{name}")
    public ResponseEntity<List<Event>> getEventsByCategoryName(@PathVariable String name) {
        List<Event> events = eventService.getEventsByCategoryName(name);
        if (events.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(events);
    }

    // GET events by mode
    @GetMapping("/mode")
    public ResponseEntity<List<Event>> getEventsByMode(@RequestParam("value") eventMode mode) {
        List<Event> events = eventService.getEventsByMode(mode);
        if (events.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(events);
    }
}
