package com.example.SmartEventManagementSystem.Controller;

import com.example.SmartEventManagementSystem.DTO.EventDTO;
import com.example.SmartEventManagementSystem.Service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO dto) {
        EventDTO created = eventService.createEvent(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Integer id, @RequestBody EventDTO dto) {
        return ResponseEntity.ok(eventService.updateEvent(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<EventDTO>> byCategory(@PathVariable String name) {
        return ResponseEntity.ok(eventService.getEventsByCategory(name));
    }

    @GetMapping("/trending")
    public ResponseEntity<List<EventDTO>> trending() {
        return ResponseEntity.ok(eventService.getTrendingEvents());
    }

    @PostMapping("/{id}/trending")
    public ResponseEntity<EventDTO> setTrending(@PathVariable Integer id, @RequestParam boolean value) {
        return ResponseEntity.ok(eventService.setTrending(id, value));
    }
}
