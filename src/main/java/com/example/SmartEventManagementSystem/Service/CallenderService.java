package com.example.SmartEventManagementSystem.Service;

import com.example.SmartEventManagementSystem.DTO.EventDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CallenderService {
    public List<EventDTO> upcomingEvents(List<EventDTO> events, int daysAhead) {
        LocalDateTime cutoff = LocalDateTime.now().plusDays(daysAhead);
        return events.stream().filter(e -> e.getEventDate() != null && e.getEventDate().isBefore(cutoff)).collect(Collectors.toList());
    }
}
