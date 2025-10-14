package com.example.SmartEventManagementSystem.DTO;

import com.example.SmartEventManagementSystem.Entities.eventMode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    @NotBlank(message = "Title is required")
    private String eventTitle;

    @NotBlank(message = "Description is required")
    private String eventDescription;;

    @NotNull(message = "Event date is required")
    private LocalDateTime eventDate;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    // Venue / Location
    @NotBlank(message = "Venue name is required")
    private String venueName;

    @NotBlank(message = "City is required")
    private String venueCity;
    @NotNull(message = "Event mode is required (ONLINE, OFFLINE, HYBRID)")
    private eventMode mode;

}
