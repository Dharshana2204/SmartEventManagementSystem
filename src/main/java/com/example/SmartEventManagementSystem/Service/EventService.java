package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.DTO.EventDTO;
import com.example.SmartEventManagementSystem.Entities.Category;
import com.example.SmartEventManagementSystem.Entities.Event;
import com.example.SmartEventManagementSystem.Entities.User;
import com.example.SmartEventManagementSystem.Entities.eventMode;
import com.example.SmartEventManagementSystem.Exception.ResourceNotFoundException;
import com.example.SmartEventManagementSystem.Repository.CatergoryRepository;
import com.example.SmartEventManagementSystem.Repository.EventRepository;
import com.example.SmartEventManagementSystem.Repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CatergoryRepository catergoryRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    private static final int trendingParticipantCount = 5;

    // Check ADMIN role
    private void checkAdminRole(String userRole) {
        if (userRole == null || !userRole.equalsIgnoreCase("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMIN users can perform this action.");
        }
    }

    // Validate venue for OFFLINE/HYBRID
    private void validateEventLocation(EventDTO dto) {
        if (dto.getMode() != eventMode.ONLINE) {
            if (dto.getVenueName() == null || dto.getVenueName().trim().isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Venue Name is required for OFFLINE and HYBRID events.");
            if (dto.getVenueCity() == null || dto.getVenueCity().trim().isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Venue City is required for OFFLINE and HYBRID events.");
        } else {
            dto.setVenueName("N/A");
            dto.setVenueCity("N/A");
        }
    }

    // Map DTO → Entity
    private Event mapDtoToEvent(EventDTO dto, Event event, Category category, User user) {
        event.setEventTitle(dto.getEventTitle());
        event.setEventDescription(dto.getEventDescription());
        event.setEventDate(dto.getEventDate());
        event.setCapacity(dto.getCapacity());
        event.setCategory(category);
        event.setVenueName(dto.getVenueName());
        event.setVenueCity(dto.getVenueCity());
        event.setMode(dto.getMode());
        event.setUser(user); // mandatory creator
        return event;
    }

    // CREATE Event - Only ADMIN
    public Event createEvent(EventDTO dto, String userRole, User user) {
        checkAdminRole(userRole);
        validateEventLocation(dto);

        Category category = catergoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", dto.getCategoryId()));

        Event event = new Event();
        mapDtoToEvent(dto, event, category, user);

        return eventRepository.save(event);
    }

    // GET all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // GET Event by ID
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "ID", id));
    }

    //GET event by name
    public List<Event> getEventsByCategoryName(String categoryName) {
        return eventRepository.findByCategoryNameIgnoreCase(categoryName);
    }
    //Get event by mode
    public List<Event> getEventsByMode(eventMode mode) {
        return eventRepository.findByMode(mode);
    }
    // UPDATE Event - Only ADMIN
    public Event updateEvent(Long id, EventDTO dto, String userRole, User user) {
        checkAdminRole(userRole);
        validateEventLocation(dto);

        Event existingEvent = getEventById(id);

        Category category = catergoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", dto.getCategoryId()));

        mapDtoToEvent(dto, existingEvent, category, user);

        return eventRepository.save(existingEvent);
    }

    // DELETE Event - Only ADMIN
    public void deleteEvent(Long id, String userRole) {
        checkAdminRole(userRole);
        Event event = getEventById(id);
        eventRepository.delete(event);
    }

    @Transactional
    public void updateEventTrendingStatus(Event event) {
        long participantCount = registrationRepository.countByEventId(event.getId());
        if (participantCount >= trendingParticipantCount && !event.isTrending()) {
            event.setTrending(true);
            eventRepository.save(event);
        }
    }
}
