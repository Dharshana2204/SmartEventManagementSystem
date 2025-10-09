package com.example.SmartEventManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Integer id;
    private String eventTitle;
    private String eventDescription;
    private LocalDateTime eventDate;
    private Integer capacity;
    private Boolean isTrending;
    private String venueName;
    private String venueAddress;
    private String venueCity;
    private String venueState;
    private String venueCountry;
    private String venueZipCode;
    private String categoryName;
}
