package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.DTO.EventDTO;
import com.example.SmartEventManagementSystem.Entities.Category;
import com.example.SmartEventManagementSystem.Entities.Event;
import com.example.SmartEventManagementSystem.Exception.ResourceNotFoundException;
import com.example.SmartEventManagementSystem.Repository.CatergoryRepository;
import com.example.SmartEventManagementSystem.Repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final CatergoryRepository catergoryRepository;

    public EventService(EventRepository eventRepository, CatergoryRepository catergoryRepository) {
        this.eventRepository = eventRepository;
        this.catergoryRepository = catergoryRepository;
    }

    public EventDTO createEvent(EventDTO dto) {
        Event event = mapToEntity(dto);
        if (dto.getCategoryName() != null && !dto.getCategoryName().isBlank()) {
            Category cat = catergoryRepository.findByName(dto.getCategoryName()).orElseGet(() -> {
                Category c = new Category();
                c.setName(dto.getCategoryName());
                return catergoryRepository.save(c);
            });
            event.setCategory(cat);
        }
        Event saved = eventRepository.save(event);
        return mapToDto(saved);
    }

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public EventDTO getEventById(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        return mapToDto(event);
    }

    public EventDTO updateEvent(Integer id, EventDTO dto) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        // update fields
        if (dto.getEventTitle() != null) event.setEventTitle(dto.getEventTitle());
        if (dto.getEventDescription() != null) event.setEventDescription(dto.getEventDescription());
        if (dto.getEventDate() != null) event.setEventDate(dto.getEventDate());
        if (dto.getCapacity() != null) event.setCapacity(dto.getCapacity());
        if (dto.getIsTrending() != null) event.setTrending(dto.getIsTrending());
        if (dto.getVenueName() != null) event.setVenueName(dto.getVenueName());
        if (dto.getVenueAddress() != null) event.setVenueAddress(dto.getVenueAddress());
        if (dto.getVenueCity() != null) event.setVenueCity(dto.getVenueCity());
        if (dto.getVenueState() != null) event.setVenueState(dto.getVenueState());
        if (dto.getVenueCountry() != null) event.setVenueCountry(dto.getVenueCountry());
        if (dto.getVenueZipCode() != null) event.setVenueZipCode(dto.getVenueZipCode());

        if (dto.getCategoryName() != null && !dto.getCategoryName().isBlank()) {
            Category cat = catergoryRepository.findByName(dto.getCategoryName()).orElseGet(() -> {
                Category c = new Category();
                c.setName(dto.getCategoryName());
                return catergoryRepository.save(c);
            });
            event.setCategory(cat);
        }

        Event saved = eventRepository.save(event);
        return mapToDto(saved);
    }

    public void deleteEvent(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        eventRepository.delete(event);
    }

    public List<EventDTO> getEventsByCategory(String categoryName) {
        return eventRepository.findByCategoryName(categoryName).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<EventDTO> getTrendingEvents() {
        return eventRepository.findByIsTrendingTrue().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public EventDTO setTrending(Integer id, boolean trending) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        event.setTrending(trending);
        return mapToDto(eventRepository.save(event));
    }

    // Mapping helpers
    private EventDTO mapToDto(Event e) {
        EventDTO dto = new EventDTO();
        dto.setId(e.getId());
        dto.setEventTitle(e.getEventTitle());
        dto.setEventDescription(e.getEventDescription());
        dto.setEventDate(e.getEventDate());
        dto.setCapacity(e.getCapacity());
        dto.setIsTrending(e.isTrending());
        dto.setVenueName(e.getVenueName());
        dto.setVenueAddress(e.getVenueAddress());
        dto.setVenueCity(e.getVenueCity());
        dto.setVenueState(e.getVenueState());
        dto.setVenueCountry(e.getVenueCountry());
        dto.setVenueZipCode(e.getVenueZipCode());
        if (e.getCategory() != null) dto.setCategoryName(e.getCategory().getName());
        return dto;
    }

    private Event mapToEntity(EventDTO dto) {
        Event e = new Event();
        e.setEventTitle(dto.getEventTitle());
        e.setEventDescription(dto.getEventDescription());
        e.setEventDate(dto.getEventDate());
        e.setCapacity(dto.getCapacity() != null ? dto.getCapacity() : 0);
        e.setTrending(dto.getIsTrending() != null && dto.getIsTrending());
        e.setVenueName(dto.getVenueName());
        e.setVenueAddress(dto.getVenueAddress());
        e.setVenueCity(dto.getVenueCity());
        e.setVenueState(dto.getVenueState());
        e.setVenueCountry(dto.getVenueCountry());
        e.setVenueZipCode(dto.getVenueZipCode());
        return e;
    }
}
