package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.DTO.EventDTO;
import com.example.SmartEventManagementSystem.Entities.Category;
import com.example.SmartEventManagementSystem.Entities.Event;
import com.example.SmartEventManagementSystem.Entities.eventMode;
import com.example.SmartEventManagementSystem.Exception.ResourceNotFoundException;
import com.example.SmartEventManagementSystem.Repository.CatergoryRepository;
import com.example.SmartEventManagementSystem.Repository.EventRepository;
import com.example.SmartEventManagementSystem.Repository.RegistrationRepository;
import jakarta.persistence.Access;
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

    private void validateEventLocation(EventDTO dto) {
        // If mode is not ONLINE, venue name and city are mandatory.
        if (dto.getMode() != eventMode.ONLINE)
        {

            // Checking for OFFLINE and HYBRID modes
            if (dto.getVenueName() == null || dto.getVenueName().trim().isEmpty())
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Venue Name is required for OFFLINE and HYBRID events.");
            }
            if (dto.getVenueCity() == null || dto.getVenueCity().trim().isEmpty())
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Venue City is required for OFFLINE and HYBRID events.");
            }
        } else
        {
            // If mode is ONLINE,the venue filed should be null.
            dto.setVenueName(null);
            dto.setVenueCity(null);
        }
    }

    // Helper method to map DTO → Entity
    private Event mapDtoToEvent(EventDTO dto, Event event, Category category)
    {
        event.setEventTitle(dto.getEventTitle());
        event.setEventDescription(dto.getEventDescription());
        event.setEventDate(dto.getEventDate());
        event.setCapacity(dto.getCapacity());
        event.setCategory(category);
        event.setVenueName(dto.getVenueName());
        event.setVenueCity(dto.getVenueCity());
        event.setMode(dto.getMode());
        return event;
    }

    public Event createEvent(EventDTO dto)
    {
        validateEventLocation(dto);
        Category category = catergoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", dto.getCategoryId()));

        Event event = new Event();
        mapDtoToEvent(dto, event, category);

        return eventRepository.save(event);
    }

    public Event getEventById(Long id)
    {

        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "ID", id));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event updateEvent(Long id, EventDTO dto)
    {
        validateEventLocation(dto);
        Event existingEvent = getEventById(id);
        Category category = catergoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", dto.getCategoryId()));

        mapDtoToEvent(dto, existingEvent, category);

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id)
    {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }

    public List<Event> getEventsByCategoryName(String categoryName)
    {
        return eventRepository.findByCategoryNameIgnoreCase(categoryName);
    }

    public List<Event> getEventsByMode(eventMode mode)
    {
        return eventRepository.findByMode(mode);
    }

    @Transactional
    public void updateEventTrendingStatus(Event event)
    {
        long participantCount = registrationRepository.countByEventId(event.getId());

        if (participantCount >= trendingParticipantCount)
        {
            if (!event.isTrending())
            {
                event.setTrending(true);
                eventRepository.save(event);
                System.out.println("Event '" + event.getEventTitle() + "' is now trending!");
            }
        }
    }
}